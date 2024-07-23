package com.gdtcs.batch.dataCollectors.service;

import com.gdtcs.batch.dataCollectors.mapper.DataCollectorMapper;

import com.gdtcs.batch.dataCollectors.type.DataProcessingType;
import com.gdtcs.batch.dataCollectors.type.ExemptionStatus;
import com.gdtcs.batch.dataCollectors.type.MeritCarResponseCodeType;
import com.gdtcs.batch.dataCollectors.type.OriginalXmlType;
import com.gdtcs.batch.dataCollectors.type.ProgressFlag;
import com.gdtcs.batch.dataCollectors.type.ResponseResultType;
import com.gdtcs.batch.dataCollectors.type.SyncStatus;
import com.gdtcs.batch.dataCollectors.type.ThreadPriority;
import com.gdtcs.batch.dataCollectors.type.VehicleType;
import com.gdtcs.batch.dataCollectors.util.Client;
import com.gdtcs.batch.dataCollectors.util.CommonUtil;
import com.gdtcs.batch.dataCollectors.util.LogFileUtil;
import com.gdtcs.batch.dataCollectors.util.NewGpkiUtil;
import com.gdtcs.batch.dataCollectors.util.PriorityExecutorService;
import com.gdtcs.batch.dataCollectors.util.ShareGpki;
import com.gdtcs.batch.dataCollectors.vo.GpkiConfig;
import com.gdtcs.batch.dataCollectors.vo.VehicleMasterVo;
import com.gdtcs.batch.dataCollectors.vo.VehicleVo;

import com.gdtcs.batch.dataCollectors.vo.xml.response.DisabilityVehicle.EnvelopeDisabilityVehicle;
import com.gdtcs.batch.dataCollectors.vo.xml.response.DisabilityVehicle.GetReductionDisabledCarYnResponse;
import com.gdtcs.batch.dataCollectors.vo.xml.response.nationalMeritVehicle.EnvelopeNationalMeritVehicle;
import com.gdtcs.batch.dataCollectors.vo.xml.response.nationalMeritVehicle.GetMeritCarYnResponse;

import jakarta.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class DataCollectorServiceImpl implements DataCollectorService {

	private final DataCollectorMapper dataCollectorMapper;
	private final PriorityExecutorService priorityExecutorService;
	private final GpkiConfig gpkiConfig;

	private static String requestXml;
	private static String responseXml;
	private static String encoded;
	private static NewGpkiUtil g;
	private static final String DELIMITER = "|";
	private static String originalXml;

	private static final String DISABLED_LOG_FILE_NAME = "disabled";
	private static final String NATIONAL_LOG_FILE_NAME = "national";

	@Autowired
	public DataCollectorServiceImpl(DataCollectorMapper dataCollectorMapper, PriorityExecutorService priorityExecutorService, GpkiConfig gpkiConfig) {
		this.dataCollectorMapper = dataCollectorMapper;
		this.priorityExecutorService = priorityExecutorService;
		this.gpkiConfig = gpkiConfig;
	}


	@Async
	@Transactional
	public CompletableFuture<GetMeritCarYnResponse> findNationalMeritVehicleAsync(String carNo) {
		return priorityExecutorService.submit(() -> findNationalMeritVehicle(carNo), ThreadPriority.MERITORIOUS_PERSON.getPriorityValue());  // Priority 1
	}

	@Async
	@Transactional
	public CompletableFuture<GetReductionDisabledCarYnResponse> findDisabilityVehicleAsync(String carNo) {
		return priorityExecutorService.submit(() -> findDisabilityVehicle(carNo), ThreadPriority.DISABLED.getPriorityValue());  // Priority 2
	}

	@Override
	public void findVehicleInfo() {
		log.debug("###### findVehicleInfo ######");

		try {

			VehicleVo reqVehicleVo =VehicleVo.builder()
				.progFlag(ProgressFlag.API_REQUEST.getCode())
				.exemptStatus(ExemptionStatus.BEFORE_QUERY.getCode())
				.vehicleType(VehicleType.GENERAL.getCode())
				.build();

			List<VehicleVo> vehicleVoList = dataCollectorMapper.selectVehicleList(reqVehicleVo);

			if (CollectionUtils.isNotEmpty(vehicleVoList)) {
				vehicleVoList.forEach( vehicleVo -> {
					try {

						VehicleMasterVo vehicleMasterVo = findVehicleMasterVo(vehicleVo);
						if (ObjectUtils.isNotEmpty(vehicleMasterVo)) {
							// 마스터 테이블에 면제 정보가 있을경우 API 요청을 하지 않는다.
							updateVehicle(vehicleVo, ProgressFlag.API_COMPLETE.getCode(), vehicleMasterVo.getExemptStatus());
						} else {
							apiCallAndDbUpdate(vehicleVo, DataProcessingType.SINGLE_UPDATE.getCode());
						}
					} catch (Exception e) {
						log.error("Error occurred while fetching vehicle information", e);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(">>>>>>>>> findVehicleInfo query error : {} ", e);
		}
	}

	/**
	 * 국가유공자차량 API 서비스 URL
	 * @return
	 */
	private String getMeriCarApiServiceUrl() {
		System.out.println("DataCollectorServiceImpl.getMeriCarApiServiceUrl");
		return String.join("http://%s%s", gpkiConfig.getPrivateIp(), "/cmc/ynservice/mpva/MeritCarYnService");

	}


	/**
	 * 장애인차량 API 서비스 URL
	 * @return
	 */
	private String getReductionDisabledCarApiServiceUrl() {

		return String.join("http://%s%s", gpkiConfig.getPrivateIp(), "/cmc/ynservice/swsdn/ReductionDisabledCarYnService");
	}


	/**
	 * 비동기로 API 호출 및 DB 업데이트
	 * @param vehicleVo
	 * @param dataProcessingType
	 */
	private void apiCallAndDbUpdate(VehicleVo vehicleVo, String dataProcessingType) {
		CompletableFuture<GetMeritCarYnResponse> meritCarFuture = findNationalMeritVehicleAsync(vehicleVo.getCarNo());
		CompletableFuture<GetReductionDisabledCarYnResponse> disabledCarFuture = findDisabilityVehicleAsync(vehicleVo.getCarNo());

		// 먼저 완료되는 작업의 결과를 처리
		CompletableFuture.anyOf(meritCarFuture, disabledCarFuture)
			.thenAccept(result -> {
				StringBuffer logMessage = new StringBuffer();
				if (result instanceof GetMeritCarYnResponse) {
					GetMeritCarYnResponse getMeritCarYnResponse = (GetMeritCarYnResponse)result;

					if (
								ObjectUtils.isNotEmpty(getMeritCarYnResponse)
							&&  StringUtils.equals(getMeritCarYnResponse.getResultCd(), ResponseResultType.RESULT_YN_Y.getCode())
							&&  StringUtils.contains(getMeritCarYnResponse.getResultMsg(), MeritCarResponseCodeType.SVR01.getCode())
					) {
						MeritCarApiLog(getMeritCarYnResponse);

						getCreateLogFormByMeritCar(vehicleVo.getCarNo(), logMessage, getMeritCarYnResponse);
						LogFileUtil.logWrite(gpkiConfig.getLogPath() ,NATIONAL_LOG_FILE_NAME, logMessage.toString(), gpkiConfig.getCharSet());

						// 면제차량 정보 Insert
						insertVehicleMaster(vehicleVo, ExemptionStatus.MERITORIOUS_PERSON.getCode());

						if (StringUtils.equals(dataProcessingType, DataProcessingType.SINGLE_UPDATE.getCode())) {
							// 차량정보 업데이트
							updateVehicle(vehicleVo, ProgressFlag.API_COMPLETE.getCode(), ExemptionStatus.MERITORIOUS_PERSON.getCode());
						}

					} else if (StringUtils.equals(getMeritCarYnResponse.getResultCd(), MeritCarResponseCodeType.ERR11.getCode())) {
						// 필수입력정보 누락
						getCreateLogFormByMeritCar(vehicleVo.getCarNo(), logMessage, getMeritCarYnResponse);
						LogFileUtil.logWrite(gpkiConfig.getLogPath() , NATIONAL_LOG_FILE_NAME, logMessage.toString(), gpkiConfig.getCharSet());
					} else if (StringUtils.equals(getMeritCarYnResponse.getResultCd(), MeritCarResponseCodeType.SVR99.getCode())) {
						// 기타 Exception 발생
						getCreateLogFormByMeritCar(vehicleVo.getCarNo(), logMessage, getMeritCarYnResponse);
						LogFileUtil.logWrite(gpkiConfig.getLogPath() , NATIONAL_LOG_FILE_NAME, logMessage.toString(), gpkiConfig.getCharSet());
					}

				} else if (result instanceof GetReductionDisabledCarYnResponse) {
					GetReductionDisabledCarYnResponse getReductionDisabledCarYnResponse = (GetReductionDisabledCarYnResponse)result;

					if (
						ObjectUtils.isNotEmpty(getReductionDisabledCarYnResponse)
							&& StringUtils.equals(getReductionDisabledCarYnResponse.getQufyYn(), ResponseResultType.RESULT_YN_Y.getCode())
					) {
						log.debug("Disability Vehicle Response received first. Car No: {}, TgtrNm: {}, ObsLvCla: {}, InqrDt: {}, QufyYn: {}",
						getReductionDisabledCarYnResponse.getCarNo(), getReductionDisabledCarYnResponse.getTgtrNm(),
						getReductionDisabledCarYnResponse.getObsLvCla(), getReductionDisabledCarYnResponse.getInqrDt(),
						getReductionDisabledCarYnResponse.getQufyYn());

						getCreateLogFormByReductionDisabledCar(vehicleVo.getCarNo(), logMessage, getReductionDisabledCarYnResponse);
						LogFileUtil.logWrite(gpkiConfig.getLogPath() , DISABLED_LOG_FILE_NAME, logMessage.toString(), gpkiConfig.getCharSet());

						// 면제차량 정보 Insert
						insertVehicleMaster(vehicleVo, ExemptionStatus.DISABLED.getCode());

						if (StringUtils.equals(dataProcessingType, DataProcessingType.SINGLE_UPDATE.getCode())) {
							// 차량정보 업데이트
							updateVehicle(vehicleVo, ProgressFlag.API_COMPLETE.getCode(), ExemptionStatus.DISABLED.getCode());
						}

					} else {
						getCreateLogFormByReductionDisabledCar(vehicleVo.getCarNo(), logMessage, getReductionDisabledCarYnResponse);
						// 조회불능
						LogFileUtil.logWrite(gpkiConfig.getLogPath() , DISABLED_LOG_FILE_NAME, logMessage.toString(), gpkiConfig.getCharSet());
					}

				}
			}).join();  // 두 작업이 모두 완료될 때까지 기다림
	}

	@Override
	public void updateAllExemptVehicle() {
		log.debug("###### updateAllExemptVehicle ######");
		VehicleMasterVo vehicleMasterVo = VehicleMasterVo.builder()
			.baseDate(DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyyMMdd"))   // 전일자 데이터만 조회
			.syncStatus(SyncStatus.SYNCHRONIZED.getCode())
			.build();

		List<VehicleMasterVo> vehicleMasterVoList = dataCollectorMapper.selectAllSyncVehicleMaster(vehicleMasterVo);
		if(CollectionUtils.isNotEmpty(vehicleMasterVoList)) {
			vehicleMasterVoList.forEach( vehicleMaster -> {
				try {
					apiCallAndDbUpdate(VehicleVo.builder().carNo(vehicleMaster.getCarNo()).build(), DataProcessingType.FULL_UPDATE.getCode());
				} catch (Exception e) {
					log.error("Error occurred while fetching vehicle information", e);
				}
			});

		}

	}

	/**
	 * 국가유공자차량 API 응답 로그
	 * @param getMeritCarYnResponse
	 */
	private static void MeritCarApiLog(GetMeritCarYnResponse getMeritCarYnResponse) {
		log.debug("National Merit Vehicle Response received first. Result Code: {}, Result Message: {}",
			getMeritCarYnResponse.getResultCd(), getMeritCarYnResponse.getResultMsg());
	}

	/**
	 * 차량정보 조회
	 * @param vehicleVo
	 * @return
	 */
	private VehicleMasterVo findVehicleMasterVo(VehicleVo vehicleVo) {
		VehicleMasterVo vehicleMasterVo = dataCollectorMapper.selectVehicleMaster(VehicleMasterVo.builder().carNo(vehicleVo.getCarNo()).build());
		return vehicleMasterVo;
	}

	/**
	 * 차량정보 Update
	 * @param vehicleVo
	 * @param progressFlag
	 * @param exemptStatus
	 */
	private void updateVehicle(VehicleVo vehicleVo, String progressFlag, String exemptStatus) {
		VehicleVo updateVehicleVo = VehicleVo.builder()
			.carNo(vehicleVo.getCarNo())
			.progFlag(ProgressFlag.API_COMPLETE.getCode())
			.exemptStatus(ExemptionStatus.MERITORIOUS_PERSON.getCode())
			.modId("SYSTEM")
			.modDt(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))
			.build();
		dataCollectorMapper.updateVehicle(updateVehicleVo);
	}

	/**
	 * 차량정보 Insert (면제차량)
	 * @param vehicleVo
	 * @param exemptStatus
	 */
	private void insertVehicleMaster(VehicleVo vehicleVo, String exemptStatus) {
		VehicleMasterVo vehicleMasterVo = VehicleMasterVo.builder()
			.carNo(vehicleVo.getCarNo())
			.baseDate(DateFormatUtils.format(new Date(), "yyyyMMdd"))
			.exemptStatus(exemptStatus)
			.vehicleKind(StringUtils.defaultString(vehicleVo.getVehicleKind()) )    // 차량종류는 차후 연동시에 값을 채워 넣는다
			.syncStatus(SyncStatus.SYNCHRONIZED.getCode())
			.regId("SYSTEM")
			.regDt(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))
			.modId("SYSTEM")
			.modDt(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))
			.build();
		dataCollectorMapper.insertVehicleMaster(vehicleMasterVo);
	}

	/**
	 * 국가유공자차량조회
	 *
	 * @param carNo
	 * @return
	 */
	private GetMeritCarYnResponse findNationalMeritVehicle(String carNo) {
		long startTime = System.currentTimeMillis();

		String transactionUniqueId = CommonUtil.generateTransactionUniqueId();

		GetMeritCarYnResponse getMeritCarYnResponse = findMeritCarYn(carNo, transactionUniqueId, getMeriCarApiServiceUrl());
		findResponseTie(startTime);
		return getMeritCarYnResponse;
	}

	private static void findResponseTie(long startTime) {
		log.debug("응답시간 : {} ms", System.currentTimeMillis() - startTime);
	}

	/**
	 * 장애인차량조회
	 *
	 * @param carNo
	 * @return
	 */
	private GetReductionDisabledCarYnResponse findDisabilityVehicle(String carNo) {
		long startTime = System.currentTimeMillis();

		String transactionUniqueId = CommonUtil.generateTransactionUniqueId();

		GetReductionDisabledCarYnResponse getReductionDisabledCarYnResponse = findDisabledCarYn(carNo, transactionUniqueId, getReductionDisabledCarApiServiceUrl());
		findResponseTie(startTime);
		return getReductionDisabledCarYnResponse;
	}


	/**
	 * 국가유공자차량조회
	 * @param carNo
	 * @param transactionUniqueId
	 * @param serviceUrl
	 * @return
	 */
	private GetMeritCarYnResponse findMeritCarYn(String carNo, String transactionUniqueId, String serviceUrl) {
		GetMeritCarYnResponse getMeritCarYnResponse = null;
		try {

			// 요청 XML 암호화 처리 및 응답 xml 수신
			findEncryptRequestResponseXml(carNo, transactionUniqueId, serviceUrl, CommonUtil.findNationalMeritVehicleRequestXml() , OriginalXmlType.NATIONAL_MERIT.getCode());
			if (StringUtils.isNotBlank(responseXml)) {
					try {
						JAXBContext jaxbContext = JAXBContext.newInstance(EnvelopeNationalMeritVehicle.class);
						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

						StringReader reader = new StringReader(responseXml);
						EnvelopeNationalMeritVehicle envelope = (EnvelopeNationalMeritVehicle)unmarshaller.unmarshal(reader);

						getMeritCarYnResponse = envelope.getBody().getGetMeritCarYnResponse();

						try {
							getMeritCarYnResponse.setResultCd(setDataDecrypted(g, getMeritCarYnResponse.getResultCd()));
							getMeritCarYnResponse.setResultCd(setDataDecrypted(g, getMeritCarYnResponse.getResultMsg()));
						} catch (Exception e) {
							log.error(">>>>> findVeteranVehicle decrypted error : {} ", e);
							e.printStackTrace();
						}

						log.debug(">>>>> decrypted Result Code: {}", getMeritCarYnResponse.getResultCd());
						log.debug(">>>>> decrypted  Result Message: {} " , getMeritCarYnResponse.getResultMsg());

					} catch (JAXBException e) {
						log.error(">>>>> findVeteranVehicle JAXBException error : {} ", e);
						e.printStackTrace();
					}
				}

		} catch (Throwable e) {
			log.error(">>>>> findVeteranVehicle encoded error : {} ", e);
			e.printStackTrace();
		}
		return getMeritCarYnResponse;
	}

	/**
	 * 요청 XML 암호화 처리 및 응답 xml 수신
	 * @param carNo
	 * @param transactionUniqueId
	 * @param serviceUrl
	 * @param sb
	 * @throws Exception
	 */
	private void findEncryptRequestResponseXml(String carNo, String transactionUniqueId, String serviceUrl, StringBuffer sb, String originalXmlType) throws Exception {
		requestXml = setCommonSetting(carNo, sb.toString(), transactionUniqueId);

		g = findgetNewGpkiUtil();

		// originalXmlType에 따라 원본 XML을 변경
		if (StringUtils.equals(originalXmlType, OriginalXmlType.NATIONAL_MERIT.getCode())) {
			originalXml = getOriginalMeritCar();
		} else if (StringUtils.equals(originalXmlType, OriginalXmlType.DISABLED.getCode())) {
			originalXml = getOriginalReductionDisabledCar();
		}

		byte[] encrypted = g.encrypt(originalXml.getBytes(gpkiConfig.getCharSet()), gpkiConfig.getCertServerId());
		byte[] signed = g.sign(encrypted);
		encoded = g.encode(signed);

		log.debug(">>>>> findEncryptRequestResponseXml originalXmlType  : {} ", originalXmlType);
		log.debug(">>>>> findEncryptRequestResponseXml original requestXml : {} ", requestXml);

		requestXml = requestXml.replace(originalXml, encoded);

		log.debug(">>>>> findEncryptRequestResponseXml encrypted requestXml : {} ", requestXml);

		responseXml = Client.doService(serviceUrl, requestXml);
		log.debug(">>>>> findEncryptRequestResponseXml responseXml : {} ", responseXml);
	}

	/**
	 * 원본 국가유공자차량 조회
	 * @return
	 */
	private static String getOriginalMeritCar() {
		return requestXml.split("<getMeritCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types\">")[1].split("</getMeritCarYn>")[0];
	}

	/**
	 * GPKI Util 생성
	 * @return
	 * @throws Exception
	 */
	private NewGpkiUtil findgetNewGpkiUtil() throws Exception {
		NewGpkiUtil g;
		ShareGpki shareGpki = ShareGpki.builder()
			.certPassword(gpkiConfig.getCertPassword())
			.serverId(gpkiConfig.getCertServerId())
			.envCertFilePathName(gpkiConfig.getEnvCertFilePathName())
			.envPrivateKeyFilePathName(gpkiConfig.getEnvPrivateKeyFilePathName())
			.sigCertFilePathName(gpkiConfig.getSigCertFilePathName())
			.sigPrivateKeyFilePathName(gpkiConfig.getSigPrivateKeyFilePathName())
			.gpkiLicPath(gpkiConfig.getGpkiLicPath())
			.build();

		g = shareGpki.getGpkiUtil();
		return g;
	}

	/**
	 * 장애인차량조회
	 * @param carNo
	 * @param transactionUniqueId
	 * @param serviceUrl
	 * @return
	 */
	private GetReductionDisabledCarYnResponse findDisabledCarYn(String carNo, String transactionUniqueId, String serviceUrl) {
		GetReductionDisabledCarYnResponse getReductionDisabledCarYnResponse = null;
		try {

			findEncryptRequestResponseXml(carNo, transactionUniqueId, serviceUrl, CommonUtil.findDisabilityVehicleRequestXml(), OriginalXmlType.DISABLED.getCode());

			if (StringUtils.isNotBlank(responseXml)) {
				try {
					JAXBContext jaxbContext = JAXBContext.newInstance(EnvelopeDisabilityVehicle.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

					StringReader reader = new StringReader(responseXml);
					EnvelopeDisabilityVehicle envelope = (EnvelopeDisabilityVehicle)unmarshaller.unmarshal(reader);

					getReductionDisabledCarYnResponse = envelope.getBody().getGetReductionDisabledCarYnResponse();

					try {
						getReductionDisabledCarYnResponse.setCarNo(setDataDecrypted(g, getReductionDisabledCarYnResponse.getCarNo()));
						getReductionDisabledCarYnResponse.setTgtrNm(setDataDecrypted(g, getReductionDisabledCarYnResponse.getTgtrNm()));
						getReductionDisabledCarYnResponse.setObsLvCla(setDataDecrypted(g, getReductionDisabledCarYnResponse.getObsLvCla()));
						getReductionDisabledCarYnResponse.setInqrDt(setDataDecrypted(g, getReductionDisabledCarYnResponse.getInqrDt()));
						getReductionDisabledCarYnResponse.setQufyYn(setDataDecrypted(g, getReductionDisabledCarYnResponse.getQufyYn()));
					} catch (Exception e) {
						log.error(">>>>> findVeteranVehicle decrypted error : {} ", e);
						e.printStackTrace();
					}

					if (log.isDebugEnabled()) {
						log.debug(">>>>> decrypted CarNo: {}", getReductionDisabledCarYnResponse.getCarNo());
						log.debug(">>>>> decrypted  TgtrNm : {} ", getReductionDisabledCarYnResponse.getTgtrNm());
						log.debug(">>>>> decrypted  ObsLvCla : {} ", getReductionDisabledCarYnResponse.getObsLvCla());
						log.debug(">>>>> decrypted  InqrDt : {} ", getReductionDisabledCarYnResponse.getInqrDt());
						log.debug(">>>>> decrypted  QufyYn : {} ", getReductionDisabledCarYnResponse.getQufyYn());
					}


				} catch (JAXBException e) {
					log.error(">>>>> findVeteranVehicle JAXBException error : {} ", e);
					e.printStackTrace();
				}
			}

		} catch (Throwable e) {
			log.error(">>>>> findVeteranVehicle encoded error : {} ", e);
			e.printStackTrace();
		}
		return getReductionDisabledCarYnResponse;
	}

	/**
	 * 원본 장애인차량 조회
	 * @return
	 */
	private static String getOriginalReductionDisabledCar() {
		return requestXml.split("<getReductionDisabledCarYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types\">")[1].split("</getReductionDisabledCarYn>")[0];
	}

	/**
	 * 데이터 복호화
	 * @param g
	 * @param encrypted
	 * @return
	 * @throws Exception
	 */
	private String setDataDecrypted(NewGpkiUtil g, String encrypted) throws Exception {
		String decrypted;
		byte[] decoded;
		decoded = g.decode(encrypted);
		byte[] validated = g.validate(decoded);
		decrypted = new String(g.decrypt(validated), gpkiConfig.getCharSet());
		return decrypted;
	}

	/**
	 * 공통코드 및 차량번호 셋팅
	 * @param carNo
	 * @param requestXml
	 * @param transactionUniqueId
	 * @return
	 */
	private String setCommonSetting(String carNo, String requestXml, String transactionUniqueId) {
		// 공통코드 및 차량번호 셋팅
		requestXml = requestXml.replace("#USE_SYSTEM_CODE#", gpkiConfig.getUseSystemCode());
		requestXml = requestXml.replace("#CERT_SERVER_ID#", gpkiConfig.getCertServerId());
		requestXml = requestXml.replace("#TRANSACTION_UNIQUE_ID#", transactionUniqueId);
		requestXml = requestXml.replace("#USER_DEPT_CODE#", gpkiConfig.getUserDeptCode());
		requestXml = requestXml.replace("#USER_NAME#", gpkiConfig.getUserName());
		requestXml = requestXml.replace("#carNo#", carNo);
		return requestXml;
	}

	/**
	 * 국가유공자차량 로그 생성
	 * @param carNo
	 * @param logMessage
	 * @param getMeritCarYnResponse
	 */
	private static void getCreateLogFormByMeritCar(String carNo, StringBuffer logMessage, GetMeritCarYnResponse getMeritCarYnResponse) {
		logMessage.append(carNo).append(DELIMITER)
			.append(getMeritCarYnResponse.getResultCd())
			.append(DELIMITER)
			.append(getMeritCarYnResponse.getResultMsg());
	}

	/**
	 * 장애인차량 로그 생성
	 * @param carNo
	 * @param logMessage
	 * @param getReductionDisabledCarYnResponse
	 */
	private static void getCreateLogFormByReductionDisabledCar(String carNo, StringBuffer logMessage, GetReductionDisabledCarYnResponse getReductionDisabledCarYnResponse) {
		logMessage.append(carNo)
			.append(DELIMITER)
			.append("qufyYn : ")
			.append(getReductionDisabledCarYnResponse.getQufyYn())
			.append(DELIMITER)
			.append("carNo : ")
			.append(getReductionDisabledCarYnResponse.getCarNo())
			.append(DELIMITER)
			.append("tgtrNm : ")
			.append(getReductionDisabledCarYnResponse.getTgtrNm())
			.append(DELIMITER)
			.append("obsLvCla : ")
			.append(getReductionDisabledCarYnResponse.getObsLvCla())
			.append(DELIMITER)
			.append("inqrDt : ")
			.append(getReductionDisabledCarYnResponse.getInqrDt());
	}

	@PreDestroy
	public void onDestroy() {
		priorityExecutorService.shutdown();
	}
}
