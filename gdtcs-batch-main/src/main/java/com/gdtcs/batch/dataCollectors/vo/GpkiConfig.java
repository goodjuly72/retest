package com.gdtcs.batch.dataCollectors.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Component
public class GpkiConfig implements Serializable {

	@Serial
	private static final long serialVersionUID = 8461347878551476460L;

	@Schema(description = "시스템코드")
	@Value("${common.useSystemCode}")
	private String useSystemCode;

	@Schema(description = "인증서서버ID")
	@Value("${common.certServerId}")
	private String certServerId;

	@Schema(description = "사용자부서코드")
	@Value("${common.userDeptCode}")
	private String userDeptCode;

	@Schema(description = "사용자명")
	@Value("${common.userName}")
	private String userName;

	@Schema(description = "내부IP")
	@Value("${common.private.ip}")
	private String privateIp;

	@Schema(description = "이용기관 서버전자서명 비밀번호")
	@Value("${common.cert.password}")
	private String certPassword;

	// 이용기관 서버인증서 경로
	@Schema(description = "서버인증서파일경로")
	@Value("${common.envCertFilePathName}")
	private String envCertFilePathName;

	@Schema(description = "서버인증서키파일경로")
	@Value("${common.envPrivateKeyFilePathName}")
	private String envPrivateKeyFilePathName;

	// 이용기관 서버전자서명 경로
	@Schema(description = "전자서명인증파일경로")
	@Value("${common.sigCertFilePathName}")
	private String sigCertFilePathName;

	@Schema(description = "전자서명키파일경로")
	@Value("${common.sigPrivateKeyFilePathName}")
	private String sigPrivateKeyFilePathName;

	@Schema(description = "GPKI 라이선스파일 경로")
	@Value("${common.gpkiLicPath}")
	private String gpkiLicPath;

	@Schema(description = "default charset")
	@Value("${common.charset}")
	private String charSet;

	@Schema(description = "로그파일경로")
	@Value("${common.log.path}")
	private String logPath;
}
