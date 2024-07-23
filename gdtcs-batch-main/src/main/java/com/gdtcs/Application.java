package com.gdtcs;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Slf4j
@SpringBootApplication
@EnableBatchProcessing
public class Application {

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(Application.class)
			.web(WebApplicationType.NONE);
		ConfigurableApplicationContext context = app.run(args);

		int exitCode = 0;

		try {
			JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
			String jobName = getJobName(args);
			if (jobName == null) {
				throw new Exception("invalid job name");
			}
			Job job = (Job) context.getBean(jobName);
			JobExecution execution = jobLauncher.run(job, getJobParameter());
			log.debug("success job: {}" , execution.getStatus());
		} catch (Exception e) {
			log.error("failed job : {}",e.getMessage());
			exitCode=1;
		} finally {
			context.close();
		}

		System.exit(exitCode);

	}

	private static String getJobName(String[] args) {
		for (String arg : args) {
			if (arg.startsWith("--job.name")) {
				return arg.split("=")[1];
			}
		}
		return null;
	}

	private static JobParameters getJobParameter() {
		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String now = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).format(simpleDateFormat);
		return new JobParametersBuilder().addString("execute.now", now).toJobParameters();
	}

}
