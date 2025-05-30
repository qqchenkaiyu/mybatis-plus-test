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
package com.baomidou.mybatisplus.generator.config.querys;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySql 表数据查询
 *
 * @author hubin
 * @since 2018-01-16
 */
public class MySqlQuery  {


    public String tablesSql() {
        return "show table status WHERE 1=1 ";
    }



    public String tableFieldsSql() {
        return "show full fields from `%s`";
    }



    public String tableName() {
        return "NAME";
    }



    public String tableComment() {
        return "COMMENT";
    }



    public String fieldName() {
        return "FIELD";
    }



    public String fieldType() {
        return "TYPE";
    }



    public String fieldComment() {
        return "COMMENT";
    }



    public String fieldKey() {
        return "KEY";
    }

}
