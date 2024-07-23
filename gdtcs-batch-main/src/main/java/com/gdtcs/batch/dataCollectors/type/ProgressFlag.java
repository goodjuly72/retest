package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum ProgressFlag {

	API_REQUEST("01" ),
	API_PROCESSING("02"),
	API_COMPLETE("03"),
	RPA_COMPLETE("04");

	private final String code;

	ProgressFlag(String code) {
		this.code = code;
	}

	public String getCode() {

		return code;
	}

	public static ProgressFlag fromCode(String code) {
		for (ProgressFlag flag : ProgressFlag.values()) {
			if (flag.getCode().equals(code)) {
				return flag;
			}
		}
		throw new IllegalArgumentException("Invalid code: " + code);
	}
}
