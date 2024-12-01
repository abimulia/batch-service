/**
 * SimpleJobConfiguration.java
 * 01-Dec-2024
 */
package com.abimulia.bs.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.batch.core.BatchStatus;

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
			try {
				String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
				String strdate = chunkContext.getStepContext().getJobParameters().get("run.date").toString();
				LocalDate date = LocalDate.parse(strdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				LocalTime currentTime = LocalTime.now();
				LocalDateTime dateTime = LocalDateTime.of(date, currentTime);
				System.out.println(String.format("Simple Batch received %s on %s.",item,dateTime));
			} catch (DateTimeParseException e) {
				System.err.println("Failed to run job ["+e.getMessage()+"]");
			}
			return RepeatStatus.FINISHED; 
		}, transactionManager).build();
	}
	
	@Bean
	public Step secondStep(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		log.debug("secondStep() jobRepository: "+ jobRepository + " transactionManager: "+transactionManager);
		boolean GOT_ERROR = true;
		return new StepBuilder("secondStep", jobRepository).tasklet((contribution, chunkContext) -> {
			if (GOT_ERROR) {
				throw new RuntimeException("Failed in second step");
			}
			String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
			LocalDateTime dateTime = LocalDateTime.now();
			System.out.println(String.format("Processing Batch item %s on %s.",item,dateTime));
			return RepeatStatus.FINISHED; 
		}, transactionManager).build();
	}
	
	@Bean
	public Step onSecondFailed(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		log.debug("onSecondFailed() jobRepository: "+ jobRepository + " transactionManager: "+transactionManager);
		return new StepBuilder("onSecondFailed", jobRepository).tasklet((contribution, chunkContext) -> {
			String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
			LocalDateTime dateTime = LocalDateTime.now();
			System.out.println(String.format("Re-Processing after fixing Batch item %s on %s.",item,dateTime));
			return RepeatStatus.FINISHED; 
		}, transactionManager).build();
	}
	
	@Bean
	public Step thirdStep(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		log.debug("thirdStep() jobRepository: "+ jobRepository + " transactionManager: "+transactionManager);
		return new StepBuilder("thirdStep", jobRepository).tasklet((contribution, chunkContext) -> {
			String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
			LocalDateTime dateTime = LocalDateTime.now();
			System.out.println(String.format("Delivering Batch item %s on %s.",item,dateTime));
			return RepeatStatus.FINISHED; 
		}, transactionManager).build();
	}
	
	@Bean
	public Job simpleJob(JobRepository jobRepository,Step firstStep,Step secondStep,Step thirdStep, Step onSecondFailed) {
		log.debug("simpleJob() jobRepository: " + jobRepository +" firstStep: "+  firstStep + " secondStep: "+  secondStep +" thirdStep: "+  thirdStep );
		return new JobBuilder("simpleJob", jobRepository)
				.start(firstStep)
				.next(secondStep).on("FAILED").to(onSecondFailed)
				.from(secondStep).on("*").to(thirdStep)
				.end()
//				.next(thirdStep)
				.build();
	}
}
