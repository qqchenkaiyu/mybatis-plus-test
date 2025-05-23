package com.baomidou.mybatisplus.generator.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PathInfoUtils {

    /**
     * 构建 pathInfo 路径信息
     */
    public static Map<String, String> buildPathInfo(GlobalConfig globalConfig) {

        Map<String, String> pathInfo = new HashMap<>();

        String outputDir = globalConfig.getJavaOutputDir();

        pathInfo.put(ConstVal.ENTITY_PATH,
                joinPath(outputDir, globalConfig.getParent() + ".entity" ));

        pathInfo.put(ConstVal.MAPPER_PATH,
                joinPath(outputDir, globalConfig.getParent() + ".mapper" ));

        pathInfo.put(ConstVal.SERVICE_PATH,
                joinPath(outputDir, globalConfig.getParent() + ".service"));

        pathInfo.put(ConstVal.SERVICE_IMPL_PATH,
                joinPath(outputDir, globalConfig.getParent() + ".service.impl"));

        pathInfo.put(ConstVal.CONTROLLER_PATH,
                joinPath(outputDir, globalConfig.getParent() + ".controller" ));

        return pathInfo;
    }


    private static String joinPath(String parentDir, String packageName) {
        if (parentDir == null || parentDir.isEmpty()) {
            parentDir = System.getProperty("java.io.tmpdir");
        }
        if (!parentDir.endsWith(File.separator)) {
            parentDir += File.separator;
        }
        return parentDir + packageName.replace('.', File.separatorChar);
    }
}
