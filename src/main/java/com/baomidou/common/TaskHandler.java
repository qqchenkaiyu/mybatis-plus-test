package com.baomidou.common;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.xxl.job.core.log.XxlJobFileAppender;

@Slf4j
@Component
public class TaskHandler {

    @XxlJob("test01Handler")
    public  void test01Handler() {
        XxlJobHelper.log("xxl-job, hello world.");
        log.info("xxl-job, hello world.");
    }
}
