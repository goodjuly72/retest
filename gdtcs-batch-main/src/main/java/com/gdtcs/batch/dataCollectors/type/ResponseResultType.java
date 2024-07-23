package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum ResponseResultType {
	RESULT_YN_Y("Y", "성공"),
	RESULT_YN_N("N", "실패");

	private String code;
	private String message;

	ResponseResultType(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
