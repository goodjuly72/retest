package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum SyncStatus {
	SYNCHRONIZED("01","동기화"),
	NOT_SYNCHRONIZED("02","미동기화"),;

	private final String code;
	private final String codeName;

	SyncStatus(String code, String codeName) {
		this.code = code;
		this.codeName = codeName;
	}
}
