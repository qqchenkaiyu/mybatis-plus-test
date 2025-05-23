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
package com.baomidou.mybatisplus.generator.engine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;


    /**
     * 模板引擎初始化
     */
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }


    /**
     * 输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                // Mp.java
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = (pathInfo.get(
                            ConstVal.ENTITY_PATH) + File.separator + entityName + suffixJavaOrKt());

                    if (isCreate(entityFile)) {
                        writer(objectMap, "/templates/entity.java.btl", entityFile);
                    }
                }
                // entity.java
                if (null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = (pathInfo.get(
                            ConstVal.MAPPER_PATH) + File.separator + entityName + "Mapper" + suffixJavaOrKt());

                    if (isCreate(mapperFile)) {
                        writer(objectMap, "/templates/mapper.java.btl", mapperFile);
                    }
                }

                // IMpService.java
                if (null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = (pathInfo.get(
                            ConstVal.SERVICE_PATH) + File.separator + entityName + "Service" + suffixJavaOrKt());
                    if (isCreate(serviceFile)) {
                        writer(objectMap, "/templates/service.java.btl", serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = (pathInfo.get(
                            ConstVal.SERVICE_IMPL_PATH) + File.separator + entityName + "ServiceImpl" + suffixJavaOrKt());
                    if (isCreate(implFile)) {
                        writer(objectMap, "/templates/serviceImpl.java.btl", implFile);
                    }
                }
                // MpController.java
                if (null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = (pathInfo.get(
                            ConstVal.CONTROLLER_PATH) + File.separator + entityName + "Controller" + suffixJavaOrKt());
                    if (isCreate(controllerFile)) {
                        writer(objectMap, "/templates/controller.java.btl", controllerFile);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }


    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;

    /**
     * 处理输出目录
     */
    public AbstractTemplateEngine mkdirs() {
        getConfigBuilder().getPathInfo().forEach((key, value) -> {
            File dir = new File(value);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug("创建目录： [" + value + "]");
                }
            }
        });
        return this;
    }


    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
        objectMap.put("config", config);
        objectMap.put("author", config.getGlobalConfig().getAuthor());
        objectMap.put("table", tableInfo);
        objectMap.put("date",  new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        objectMap.put("Entity", tableInfo.getEntityName());
        objectMap.put("entity", StringUtils.firstToLowerCase(tableInfo.getEntityName()));
        return objectMap;
    }



    /**
     * 检测文件是否存在
     *
     * @return 文件是否存在
     */
    protected boolean isCreate(String filePath) {
        // 自定义判断
        // 全局判断【默认】
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            file.getParentFile().mkdirs();
        }
        return !exist || getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return ConstVal.JAVA_SUFFIX;
    }


    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }
}
