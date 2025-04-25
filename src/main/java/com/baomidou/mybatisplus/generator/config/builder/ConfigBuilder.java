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
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.IDbQuery;
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
     * 模板路径配置信息
     */
    private final TemplateConfig template=new TemplateConfig();
    /**
     * 数据库配置
     */
    private final DataSourceConfig dataSourceConfig;
    /**
     * SQL连接
     */
    private Connection connection;
    /**
     * SQL语句类型
     */
    private IDbQuery dbQuery;
    private DbType dbType;
    private String  superMapperClass = ConstVal.SUPER_MAPPER_CLASS;
    /**
     * service超类定义
     */
    private String superServiceClass = ConstVal.SUPER_SERVICE_CLASS;

    private String superServiceImplClass = ConstVal.SUPER_SERVICE_IMPL_CLASS;
    /**
     * 数据库表信息
     */
    private List<TableInfo> tableInfoList;
    /**
     * 包配置详情
     */
    private Map<String, String> packageInfo;
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;
    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;
    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig = new GlobalConfig();


    /**
     * 过滤正则
     */
    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()-_=+\\\\|[{}];:\\'\\\",<.>/?]+");

    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param template         模板配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig,
                         TemplateConfig template, GlobalConfig globalConfig) {
        // 全局配置
        if (null != globalConfig)
            this.globalConfig = globalConfig;
        // 包配置
        if (null == packageConfig) {
            handlerPackage(this.template, this.globalConfig, new PackageConfig());
        } else {
            handlerPackage(this.template, this.globalConfig, packageConfig);
        }
        this.dataSourceConfig = dataSourceConfig;
        handlerDataSource(dataSourceConfig);
        // 策略配置
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }
        tableInfoList = getTablesInfo(this.strategyConfig);
    }

    // ************************ 曝露方法 BEGIN*****************************

    /**
     * 所有包配置信息
     *
     * @return 包配置
     */
    public Map<String, String> getPackageInfo() {
        return packageInfo;
    }


    /**
     * 所有路径配置
     *
     * @return 路径配置
     */
    public Map<String, String> getPathInfo() {
        return pathInfo;
    }




    public String getSuperMapperClass() {
        return superMapperClass;
    }


    /**
     * 获取超类定义
     *
     * @return 完整超类名称
     */
    public String getSuperServiceClass() {
        return superServiceClass;
    }


    public String getSuperServiceImplClass() {
        return superServiceImplClass;
    }


    /**
     * 表信息
     *
     * @return 所有表信息
     */
    public List<TableInfo> getTableInfoList() {
        return tableInfoList;
    }

    public ConfigBuilder setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
        return this;
    }


    /**
     * 模板路径配置信息
     *
     * @return 所以模板路径配置信息
     */
    public TemplateConfig getTemplate() {
        return template == null ? new TemplateConfig() : template;
    }

    // ****************************** 曝露方法 END**********************************

    /**
     * 处理包配置
     *  @param template  TemplateConfig
     * @param globalConfig
     * @param config    PackageConfig
     */
    private void handlerPackage(TemplateConfig template, GlobalConfig globalConfig, PackageConfig config) {
        // 包信息
        packageInfo = new HashMap<>(10);
        packageInfo.put(ConstVal.MODULE_NAME, config.getModuleName());
        packageInfo.put(ConstVal.ENTITY, joinPackage(config.getParent(), config.getEntity()));
        packageInfo.put(ConstVal.MAPPER, joinPackage(config.getParent(), config.getMapper()));
        packageInfo.put(ConstVal.XML, joinPackage(config.getParent(), config.getXml()));
        packageInfo.put(ConstVal.SERVICE, joinPackage(config.getParent(), config.getService()));
        packageInfo.put(ConstVal.SERVICE_IMPL, joinPackage(config.getParent(), config.getServiceImpl()));
        packageInfo.put(ConstVal.CONTROLLER, joinPackage(config.getParent(), config.getController()));

        // 自定义路径
        Map<String, String> configPathInfo = config.getPathInfo();
        if (null != configPathInfo) {
            pathInfo = configPathInfo;
        } else {
            // 生成路径信息
            pathInfo = new HashMap<>(10);
            setPathInfo(pathInfo, template.getEntity(), globalConfig.getJavaOutputDir(), ConstVal.ENTITY_PATH, ConstVal.ENTITY);
            setPathInfo(pathInfo, template.getMapper(), globalConfig.getJavaOutputDir(), ConstVal.MAPPER_PATH, ConstVal.MAPPER);
            setPathInfo(pathInfo, template.getXml(), globalConfig.getResourceOutputDir(), ConstVal.XML_PATH, ConstVal.XML);
            setPathInfo(pathInfo, template.getService(), globalConfig.getJavaOutputDir(), ConstVal.SERVICE_PATH, ConstVal.SERVICE);
            setPathInfo(pathInfo, template.getServiceImpl(), globalConfig.getJavaOutputDir(), ConstVal.SERVICE_IMPL_PATH, ConstVal.SERVICE_IMPL);
            setPathInfo(pathInfo, template.getController(), globalConfig.getJavaOutputDir(), ConstVal.CONTROLLER_PATH, ConstVal.CONTROLLER);
        }
    }

    private void setPathInfo(Map<String, String> pathInfo, String template, String outputDir, String path, String module) {
        if (StringUtils.isNotBlank(template)) {
            pathInfo.put(path, joinPath(outputDir, packageInfo.get(module)));
        }
    }

    /**
     * 处理数据源配置
     *
     * @param config DataSourceConfig
     */
    private void handlerDataSource(DataSourceConfig config) {
        connection = config.getConn();
        dbType = config.getDbType();
        dbQuery = config.getDbQuery();
    }

    /**
     * 处理表对应的类名称
     *
     * @param tableList 表名称
     * @param config    策略配置项
     * @return 补充完整信息后的表
     */
    private List<TableInfo> processTable(List<TableInfo> tableList, StrategyConfig config) {

        for (TableInfo tableInfo : tableList) {
            String entityName;
            entityName = NamingStrategy.capitalFirst(processName(tableInfo.getName(), config.getNaming()));
            tableInfo.setEntityName(entityName);
            tableInfo.setControllerName(entityName + ConstVal.CONTROLLER);
            // 检测导入包
            checkImportPackages(tableInfo);
        }
        return tableList;
    }

    /**
     * 检测导入包
     *
     * @param tableInfo ignore
     */
    private void checkImportPackages(TableInfo tableInfo) {
        if (StringUtils.isNotBlank(strategyConfig.getVersionFieldName())
                && CollectionUtils.isNotEmpty(tableInfo.getFields())) {
            tableInfo.getFields().forEach(f -> {
                if (strategyConfig.getVersionFieldName().equals(f.getName())) {
                    tableInfo.getImportPackages().add(com.baomidou.mybatisplus.annotation.Version.class.getCanonicalName());
                }
            });
        }
    }


    /**
     * 获取所有的数据库表信息
     */
    private List<TableInfo> getTablesInfo(StrategyConfig config) {
        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();
        //不存在的表名
        Set<String> notExistTables = Arrays.stream(config.getInclude()).collect(Collectors.toSet());
        try {
            //根据不同数据库类型得到不同表查询sql
            StringBuilder sql = new StringBuilder(dbQuery.tablesSql(dataSourceConfig));
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String tableName = results.getString(dbQuery.tableName());
                    if (StringUtils.isNotBlank(tableName)) {
                        TableInfo tableInfo = new TableInfo();
                        tableInfo.setName(tableName);
                        tableInfo.setComment(results.getString(dbQuery.tableComment()));
                        for (String includeTable : config.getInclude()) {
                            // 忽略大小写等于 或 正则 true
                            if (tableNameMatches(includeTable, tableName)) {
                                tableList.add(tableInfo);
                                notExistTables.remove(tableInfo.getName());
                            }
                        }
                    } else {
                        System.err.println("当前数据库为空！！！");
                    }
                }
            }
            if (notExistTables.size() > 0) {
                System.err.println("表 " + notExistTables + " 在数据库中不存在！！！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            // 性能优化，只处理需执行表字段 github issues/219
            tableList.forEach(ti -> convertTableFields(ti, config));

        return processTable(tableList, config);
    }


    /**
     * 表名匹配
     *
     * @param setTableName 设置表名
     * @param dbTableName  数据库表单
     * @return ignore
     */
    private boolean tableNameMatches(String setTableName, String dbTableName) {
        return setTableName.equalsIgnoreCase(dbTableName);
    }

    /**
     * 将字段信息与表信息关联
     *
     * @param tableInfo 表信息
     * @param config    命名策略
     * @return ignore
     */
    private TableInfo convertTableFields(TableInfo tableInfo, StrategyConfig config) {
        boolean haveId = false;
        List<TableField> fieldList = new ArrayList<>();
        DbType dbType = this.dbType;
        String tableName = tableInfo.getName();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            if (DbType.POSTGRE_SQL == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            }else if (DbType.ORACLE == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql.replace("#schema", dataSourceConfig.getSchemaName()), tableName);
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            }
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
                    ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    TableField field = new TableField();
                    String columnName = results.getString(dbQuery.fieldName());
                    // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                    String key = results.getString(dbQuery.fieldKey());
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
                    field.setType(results.getString(dbQuery.fieldType()));
                    field.setPropertyName(strategyConfig, processName(field.getName(), config.getColumnNaming()));
                    field.setColumnType(dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field));
                    field.setComment(results.getString(dbQuery.fieldComment()));
                    // 填充逻辑判断
                    List<TableFill> tableFillList = getStrategyConfig().getTableFillList();
                    if (null != tableFillList) {
                        // 忽略大写字段问题
                        tableFillList.stream().filter(tf -> tf.getFieldName().equalsIgnoreCase(field.getName()))
                                .findFirst().ifPresent(tf -> field.setFill(tf.getFieldFill().name()));
                    }
                    fieldList.add(field);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception：" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        return tableInfo;
    }


    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }


    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent, String subPackage) {
        if (StringUtils.isBlank(parent)) {
            return subPackage;
        }
        return parent + StringPool.DOT + subPackage;
    }


    /**
     * 处理表/字段名称
     *
     * @param name     ignore
     * @param strategy ignore
     * @return 根据策略返回处理后的名称
     */
    private String processName(String name, NamingStrategy strategy) {
        String propertyName;
 if (strategy == NamingStrategy.underline_to_camel) {
            // 下划线转驼峰
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            // 不处理
            propertyName = name;
        }
        return propertyName;
    }


    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }


    public ConfigBuilder setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }


    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }


    public ConfigBuilder setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }




}
