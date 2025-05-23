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
package com.baomidou.mybatisplus.generator.config.po;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.google.common.base.Joiner;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 表信息，关联到当前字段信息
 *
 * @author YangHu
 * @since 2016/8/30
 */
@Data
@Accessors(chain = true)
public class TableInfo {

    private String name;
    private String comment;
    private String entityName;
    private List<TableField> fields;

    private String fieldNames="";

    public TableInfo setEntityName( String entityName) {
        this.entityName = entityName;
        return this;
    }

    public TableInfo setFields(List<TableField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            this.fields = fields;
        }
        return this;
    }

}
