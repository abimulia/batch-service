/**
 * SimpleDecider.java
 * 01-Dec-2024
 */
package com.abimulia.bs.decider;

import java.time.LocalDateTime;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import lombok.extern.slf4j.Slf4j;

/**
 * @author abimu
 *
 * @version 1.0 (01-Dec-2024)
 * @since 01-Dec-2024 3:17:16 PM
 * 
 * 
 * Copyright(c) 2024 Abi Mulia
 */
@Slf4j
public class SimpleDecider implements JobExecutionDecider {

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		String result = LocalDateTime.now().getHour() < 12 ? "PRESENT":"NOT_PRESENT";
		System.out.println("==Decider result is " + result);
		return new FlowExecutionStatus(result);
	}

}
