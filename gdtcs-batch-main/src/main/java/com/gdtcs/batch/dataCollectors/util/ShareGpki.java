package com.gdtcs.batch.dataCollectors.util;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShareGpki {

	@Schema(description = "이용기관 서버전자서명 비밀번호")
	private String certPassword;

	@Schema(description = "이용기관 서버 ID")
	private String serverId;

	// 이용기관 서버인증서 경로
	@Schema(description = "서버인증서파일경로")
	private String envCertFilePathName;

	@Schema(description = "서버인증서키파일경로")
	private String envPrivateKeyFilePathName;

	// 이용기관 서버전자서명 경로
	@Schema(description = "전자서명인증파일경로")
	private String sigCertFilePathName;

	@Schema(description = "전자서명키파일경로")
	private String sigPrivateKeyFilePathName;

	@Schema(description = "GPKI 라이선스파일 경로")
	private String gpkiLicPath;

	public ShareGpki(){

	}

	@Builder
	public ShareGpki(String certPassword, String serverId, String envCertFilePathName, String envPrivateKeyFilePathName, String sigCertFilePathName, String sigPrivateKeyFilePathName, String gpkiLicPath){
		this.certPassword = certPassword;
		this.serverId = serverId;
		this.envCertFilePathName = envCertFilePathName;
		this.envPrivateKeyFilePathName = envPrivateKeyFilePathName;
		this.sigCertFilePathName = sigCertFilePathName;
		this.sigPrivateKeyFilePathName = sigPrivateKeyFilePathName;
		this.gpkiLicPath = gpkiLicPath;
	}


	public NewGpkiUtil getGpkiUtil()throws Exception{
		NewGpkiUtil g = new NewGpkiUtil();

		// 이용기관 GPKI API 라이선스파일 경로
		g.setGpkiLicPath(gpkiLicPath);
		g.setEnvCertFilePathName(envCertFilePathName);
		g.setEnvPrivateKeyFilePathName(envPrivateKeyFilePathName);
		g.setEnvPrivateKeyPasswd(certPassword);
		// LDAP 의 사용유무
		// 미사용일를 경우 암호화할 타겟의 인증서 파일로 저장해놓고 사용하여야함.
		g.setIsLDAP(true);
		g.setMyServerId(serverId);
		g.setSigCertFilePathName(sigCertFilePathName);
		g.setSigPrivateKeyFilePathName(sigPrivateKeyFilePathName);
		g.setSigPrivateKeyPasswd(certPassword);

		g.setTargetServerIdList(serverId);

		g.init();
		return g;
	}
}

