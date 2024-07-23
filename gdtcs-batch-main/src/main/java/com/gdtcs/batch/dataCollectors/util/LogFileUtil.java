package com.gdtcs.batch.dataCollectors.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LogFileUtil {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static LocalDate lastLogDate = LocalDate.now();

	public static void logWrite(String logDir, String baseLogFileName, String message, String charset) {
		LocalDate today = LocalDate.now();

		if (!today.equals(lastLogDate)) {
			lastLogDate = today;
		}

		String logDirPath = today.format(DATE_FORMATTER);
		String logFileName = String.format("%s_%s.log", baseLogFileName, today.format(FILE_DATE_FORMATTER));
		String logFilePath = String.format("%s/%s/%s", logDir, logDirPath, logFileName);

		try {
			File logDirFile = new File(logDir + "/" + logDirPath);
			if (!logDirFile.exists()) {
				FileUtils.forceMkdir(logDirFile);
			}

			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
			String logMessage = String.format("%s %s%n", timestamp, message);
			FileUtils.writeStringToFile(logFile, logMessage, charset, true); // 메시지를 추가
		} catch (IOException e) {
			log.error("Error writing log message", e);
		}
	}
}
