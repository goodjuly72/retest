package com.gdtcs.batch.dataCollectors.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {

	/**
	 * Generate unique transaction id
	 *
	 * @return unique transaction id
	 */
	public static String generateTransactionUniqueId() {
		String randomNumberPrefix = Double.toString(java.lang.Math.random()).substring(2, 6);
		String randomNumberSuffix = Double.toString(java.lang.Math.random()).substring(2, 6);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);
		String currentDate = formatter.format(new Date());

		StringBuffer transactionId = new StringBuffer();
		transactionId.append(currentDate);
		transactionId.append(randomNumberPrefix);
		transactionId.append(randomNumberSuffix);

		return transactionId.toString();
	}


	/**
	 * 국가유공자차량요금감면조회 요청 XML 생성
	 * @return
	 */
	public static StringBuffer findNationalMeritVehicleRequestXml() {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append(" <Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">  \n");
		sb.append("	    <Header> \n");
		sb.append("		    <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">   \n");
		sb.append("		        <serviceName>MeritCarYnService</serviceName>   \n");
		sb.append("		        <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode> \n");
		sb.append("		        <certServerId>#CERT_SERVER_ID#</certServerId>    \n");
		sb.append("		        <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId> \n");
		sb.append("		        <userDeptCode>#USER_DEPT_CODE#</userDeptCode>   \n");
		sb.append("		        <userName>#USER_NAME#</userName>    \n");
		sb.append("		    </commonHeader>   \n");
		sb.append("     </Header>    \n");
		sb.append("     <Body>   \n");
		sb.append("         <getMeritCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">  \n");
		sb.append("		        <carNo>#carNo#</carNo>    \n");
		sb.append("		    </getMeritCarYn>  \n");
		sb.append("     </Body>  \n");
		sb.append(" </Envelope> \n");
		return sb;
	}

	/**
	 * 장애인차량요금감면조회 요청 XML 생성
	 * @return
	 */
	public static StringBuffer findDisabilityVehicleRequestXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
		sb.append(" <Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n");
		sb.append("     <Header>\n");
		sb.append("         <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types\">\n");
		sb.append("             <serviceName>ReductionDisabledCarYnService</serviceName>   \n");
		sb.append("		         <useSystemCode>#USE_SYSTEM_CODE#</useSystemCode> \n");
		sb.append("		         <certServerId>#CERT_SERVER_ID#</certServerId>    \n");
		sb.append("		         <transactionUniqueId>#TRANSACTION_UNIQUE_ID#</transactionUniqueId> \n");
		sb.append("		         <userDeptCode>#USER_DEPT_CODE#</userDeptCode>   \n");
		sb.append("		         <userName>#USER_NAME#</userName>    \n");
		sb.append("         </commonHeader>\n");
		sb.append("     </Header>\n");
		sb.append("     <Body>\n");
		sb.append("         <getReductionDisabledCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types\">\n");
		sb.append("		        <ReqOrgCd></ReqOrgCd>\n");
		sb.append("		        <ReqBizCd></ReqBizCd>\n");
		sb.append("		        <CAR_NO>#carNo#</CAR_NO>\n");
		sb.append("         </getReductionDisabledCarYn>\n");
		sb.append("     </Body>\n");
		sb.append(" </Envelope>\n");
		return sb;
	}
}
