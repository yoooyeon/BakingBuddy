package com.coco.bakingbuddy.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Bean
    public Job sendAlarmJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        Job job = new JobBuilder("sendAlarmJob", jobRepository)
                .start(sendAlarmStep(jobRepository, transactionManager))
                .build();
        return job;
    }

    public Step sendAlarmStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        Step step = new StepBuilder("sendAlarmStep", jobRepository)
                .tasklet(sendAlarmTasklet(), transactionManager)
                .build();
        return step;
    }

    public Tasklet sendAlarmTasklet() {
        return ((contribution, chunkContext) -> {
            log.info(">>>>>> sendAlarmJob testTasklet ");
            // 원하는 비지니스 로직 작성
            return RepeatStatus.FINISHED;
        });
    }
}
