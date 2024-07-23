package com.gdtcs.batch.dataCollectors.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class PrioritizedTask<T> implements Runnable, Comparable<PrioritizedTask<T>> {
	private final Callable<T> task;
	private final int priority;
	private CompletableFuture<T> future;

	public PrioritizedTask(Callable<T> task, int priority) {
		this.task = task;
		this.priority = priority;
		this.future = new CompletableFuture<>();
	}

	@Override
	public void run() {
		try {
			T result = task.call();
			future.complete(result);
		} catch (Exception e) {
			future.completeExceptionally(e);
		}
	}

	@Override
	public int compareTo(PrioritizedTask<T> o) {
		return Integer.compare(o.priority, this.priority); // 높은 우선순위가 먼저 실행되도록
	}

	public CompletableFuture<T> getFuture() {
		return future;
	}
}
