package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum ExemptionStatus {
	NOT_EXEMPT("01","면제아님"),
	UNABLE_TO_QUERY("02","조회불능"),
	MULTI_CHILD("03","다자녀"),
	MERITORIOUS_PERSON("04","유공자"),
	DISABLED("05","장애인"),
	ELECTRIC_BUSAN("06","전기(부산)"),
	HYDROGEN_BUSAN("07","수소(부산)"),
	EMPTY_TAXI("08","공차(택시)"),
	BEFORE_QUERY("99","면제여부 조회전");

	private final String code;
	private final String message;

	ExemptionStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ExemptionStatus fromCode(String code) {
		for (ExemptionStatus status : ExemptionStatus.values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid code: " + code);
	}
}
