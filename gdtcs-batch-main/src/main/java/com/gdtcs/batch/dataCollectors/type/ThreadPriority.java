package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum ThreadPriority {
	MULTIPLE_CHILDREN(1, "다자녀"),
	MERITORIOUS_PERSON(2, "유공자"),
	DISABLED(3, "장애인"),
	ELECTRIC(4, "전기"),
	HYDROGEN(5, "수소");

	private final int priorityValue;
	private final String description;

	ThreadPriority(int priorityValue, String description) {
		this.priorityValue = priorityValue;
		this.description = description;
	}
}
