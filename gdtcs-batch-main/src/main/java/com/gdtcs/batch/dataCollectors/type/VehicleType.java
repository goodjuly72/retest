package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum VehicleType {
	GENERAL("01", "일반"),
	TAXI("02", "택시");

	private final String code;
	private final String description;

	VehicleType(String code, String description) {
		this.code = code;
		this.description = description;
	}
}
