package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum MeritCarResponseCodeType {
	SVR01("SVR01", "정상처리"),
	ERR11("ERR11", "필수입력정보 누락"),
	SVR99("SVR99", "기타 Exception 발생");

	private String code;
	private String message;

	MeritCarResponseCodeType(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
