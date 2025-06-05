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
    public  void test01Handler() throws InterruptedException {
      //  XxlJobHelper.log("xxl-job, hello world.");
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        log.info("xxl-job, shardIndex: {}, shardTotal: {}", shardIndex, shardTotal);
        try {
            Thread.sleep(10000);
        }catch (Exception e){
            log.info("xxl-job, 被中止啦");
        }

        log.info("xxl-job, end");
    }
}
