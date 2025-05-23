/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config.builder;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 配置汇总 传递给文件生成工具
 *
 * @author YangHu, tangguo, hubin, Juzi
 * @since 2016-08-30
 */
public class ConfigBuilder {

    /**
     * 数据库配置
     */
    private final DataSourceConfig dataSourceConfig;

    /**
     * 数据库表信息
     */
    private List<TableInfo> tableInfoList;
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig = new GlobalConfig();


    /**
     * 在构造器中处理配置
     *
     * @param dataSourceConfig 数据源配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilder(DataSourceConfig dataSourceConfig, GlobalConfig globalConfig) {
        // 全局配置
        if (null != globalConfig)
            this.globalConfig = globalConfig;
        // 包配置
        pathInfo = PathInfoUtils.buildPathInfo(this.globalConfig);
        this.dataSourceConfig = dataSourceConfig;
        tableInfoList = getTablesInfo();
    }


    /**
     * 所有路径配置
     *
     * @return 路径配置
     */
    public Map<String, String> getPathInfo() {
        return pathInfo;
    }


    /**
     * 表信息
     *
     * @return 所有表信息
     */
    public List<TableInfo> getTableInfoList() {
        return tableInfoList;
    }


    /**
     * 处理表对应的类名称
     *
     * @param tableList 表名称
     * @return 补充完整信息后的表
     */
    private List<TableInfo> processTable(List<TableInfo> tableList) {

        for (TableInfo tableInfo : tableList) {
            String entityName;
            entityName = NamingStrategy.capitalFirst(NamingStrategy.underlineToCamel(tableInfo.getName()));
            tableInfo.setEntityName(entityName);
        }
        return tableList;
    }


    /**
     * 获取所有的数据库表信息
     */
    private List<TableInfo> getTablesInfo() {
        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();
        try {
            //根据不同数据库类型得到不同表查询sql
            StringBuilder sql = new StringBuilder(dataSourceConfig.getDbQuery().tablesSql(dataSourceConfig));
            try (PreparedStatement preparedStatement = dataSourceConfig.getConn()
                    .prepareStatement(sql.toString()); ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String tableName = results.getString(dataSourceConfig.getDbQuery().tableName());
                    if (StringUtils.isNotBlank(tableName)) {
                        TableInfo tableInfo = new TableInfo();
                        tableInfo.setName(tableName);
                        tableInfo.setComment(results.getString(dataSourceConfig.getDbQuery().tableComment()));
                        tableList.add(tableInfo);
                    } else {
                        System.err.println("当前数据库为空！！！");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 性能优化，只处理需执行表字段 github issues/219
        tableList.forEach(ti -> convertTableFields(ti));

        return processTable(tableList);
    }


    /**
     * 表名匹配
     *
     * @param setTableName 设置表名
     * @param dbTableName  数据库表单
     * @return ignore
     */
    private boolean tableNameMatches(String setTableName, String dbTableName) {
        return setTableName.equals("*") || setTableName.equalsIgnoreCase(dbTableName);
    }

    /**
     * 将字段信息与表信息关联
     *
     * @param tableInfo 表信息
     * @return ignore
     */
    private TableInfo convertTableFields(TableInfo tableInfo) {
        boolean haveId = false;
        List<TableField> fieldList = new ArrayList<>();
        String tableName = tableInfo.getName();
        try {
            String tableFieldsSql = String.format(dataSourceConfig.getDbQuery().tableFieldsSql(), tableName);
            try (PreparedStatement preparedStatement = dataSourceConfig.getConn()
                    .prepareStatement(tableFieldsSql); ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    TableField field = new TableField();
                    String columnName = results.getString(dataSourceConfig.getDbQuery().fieldName());
                    // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                    String key = results.getString(dataSourceConfig.getDbQuery().fieldKey());
                    boolean isId = StringUtils.isNotBlank(key) && "PRI".equals(key.toUpperCase());
                    // 处理ID
                    if (isId && !haveId) {
                        field.setKeyFlag(true);
                        haveId = true;
                    } else {
                        field.setKeyFlag(false);
                    }
                    // 处理其它信息
                    field.setName(columnName);
                    field.setType(results.getString(dataSourceConfig.getDbQuery().fieldType()));
                    field.setPropertyName(NamingStrategy.underlineToCamel(field.getName()));
                    field.setColumnType(dataSourceConfig.getTypeConvert().processTypeConvert(field.getType()));
                    field.setComment(results.getString(dataSourceConfig.getDbQuery().fieldComment()));
                    fieldList.add(field);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception：" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        return tableInfo;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }


}
