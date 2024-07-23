package com.gdtcs.batch.dataCollectors.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CodeVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 8550483536151224991L;

	@Schema(description = "메인코드")
	private String mainCode;

	@Schema(description = "서브코드")
	private String subCode;

	@Schema(description = "'코드명'")
	private String codeName;

	@Schema(description = "작성자")
	private String author;

	@Schema(description = "최초작성일")
	private String regDate;

	@Schema(description = "수정일")
	private String modiDate;

}
