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
package com.baomidou.mybatisplus.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 全局配置
 *
 * @author hubin
 * @since 2016-12-02
 */
@Data
@Accessors(chain = true)
public class GlobalConfig {
    private String parent = "";
    /**
     * 生成文件的输出目录【默认 D 盘根目录】
     */
    private String javaOutputDir = System.getProperty("user.dir") + "/src/main/java";
    private String resourceOutputDir = System.getProperty("user.dir") + "/src/main/resources";
    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = true;

    /**
     * 开发人员
     */
    private String author;
}
