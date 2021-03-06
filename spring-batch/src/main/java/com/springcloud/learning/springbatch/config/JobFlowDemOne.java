package com.springcloud.learning.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobFlowDemOne {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job JobFlowDemo1() {
        return jobBuilderFactory.get("JobFlowDemo1")
                .start(JobFlowDemoStep1())
                .on("COMPLETED")
                .to(JobFlowDemoStep2())
                .from(JobFlowDemoStep2())
                .on("COMPLETED")
                .to(JobFlowDemoStep3())
                .from(JobFlowDemoStep3())
                .end()
                .build();

    }

    @Bean
    public Step JobFlowDemoStep1() {
        return stepBuilderFactory.get("JobFlowDemoStep1").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("JobFlowDemoStep1-->Hello Spring Batch....");
                return RepeatStatus.FINISHED;
            }
        }).build();

    }

    @Bean
    public Step JobFlowDemoStep2() {
        return stepBuilderFactory.get("JobFlowDemoStep2").tasklet((contribution, context) -> {
            System.out.println("JobFlowDemoStep2-->Hello Spring Batch..");
            return RepeatStatus.FINISHED;
        }).build();

    }

    @Bean
    public Step JobFlowDemoStep3() {
        return stepBuilderFactory.get("JobFlowDemoStep3").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("JobFlowDemoStep3-->Hello Spring Batch....");
                return RepeatStatus.FINISHED;
            }
        }).build();

    }
}