/**
 * SimpleJobConfiguration.java
 * 01-Dec-2024
 */
package com.abimulia.bs.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @author abimu
 *
 * @version 1.0 (01-Dec-2024)
 * @since 01-Dec-2024 9:20:20 AM
 * 
 * 
 * Copyright(c) 2024 Abi Mulia
 */
@Configuration
@Slf4j
public class SimpleJobConfiguration {
	
	@Bean
	public Step firstStep(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		log.debug("firstStep() jobRepository: "+ jobRepository + " transactionManager: "+transactionManager);
		return new StepBuilder("firstStep", jobRepository).tasklet((contribution, chunkContext) -> {
			System.out.println("Simple Batch");
			return RepeatStatus.FINISHED; 
		}, transactionManager).allowStartIfComplete(true).build();
	}

	@Bean
	public Job simpleJob(JobRepository jobRepository, Step firstStep) {
		log.debug("simpleJob() jobRepository: " + jobRepository +" firstStep: "+  firstStep);
		return new JobBuilder("simpleJob", jobRepository).start(firstStep).build();
	}

}
