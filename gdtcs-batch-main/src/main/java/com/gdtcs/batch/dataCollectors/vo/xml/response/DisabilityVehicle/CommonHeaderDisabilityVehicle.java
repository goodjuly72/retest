package com.gdtcs.batch.dataCollectors.vo.xml.response.DisabilityVehicle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@XmlType(propOrder = {
	"serviceName",
	"useSystemCode",
	"certServerId",
	"transactionUniqueId",
	"userDeptCode",
	"userName"
})
public class CommonHeaderDisabilityVehicle {

	private String serviceName;
	private String useSystemCode;
	private String certServerId;
	private String transactionUniqueId;
	private String userDeptCode;
	private String userName;

	@XmlElement(name = "serviceName", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getServiceName() {
		return serviceName;
	}

	@XmlElement(name = "useSystemCode", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getUseSystemCode() {
		return useSystemCode;
	}

	public void setUseSystemCode(String useSystemCode) {
		this.useSystemCode = useSystemCode;
	}

	@XmlElement(name = "certServerId", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getCertServerId() {
		return certServerId;
	}

	@XmlElement(name = "transactionUniqueId", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getTransactionUniqueId() {
		return transactionUniqueId;
	}

	@XmlElement(name = "userDeptCode", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getUserDeptCode() {
		return userDeptCode;
	}

	@XmlElement(name = "userName", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getUserName() {
		return userName;
	}
}
