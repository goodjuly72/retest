package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum DataProcessingType {
	FULL_UPDATE("ALL"),
	SINGLE_UPDATE("FIRST");

	private final String code;

	DataProcessingType(String code) {
		this.code = code;
	}
}
