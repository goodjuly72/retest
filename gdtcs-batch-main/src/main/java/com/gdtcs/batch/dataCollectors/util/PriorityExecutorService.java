package com.gdtcs.batch.dataCollectors.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityExecutorService {
	private final ThreadPoolExecutor executorService;
	private final int WAIT_TIME_SECOND = 60;

	public PriorityExecutorService(int poolSize) {
		this.executorService = new ThreadPoolExecutor(
			poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
	}

	public <T> CompletableFuture<T> submit(Callable<T> task, int priority) {
		PrioritizedTask<T> prioritizedTask = new PrioritizedTask<>(task, priority);
		executorService.execute(prioritizedTask);
		return prioritizedTask.getFuture();
	}

	public void shutdown() {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(WAIT_TIME_SECOND, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
				if (!executorService.awaitTermination(WAIT_TIME_SECOND, TimeUnit.SECONDS))
					System.err.println("ExecutorService did not terminate");
			}
		} catch (InterruptedException ie) {
			executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}
