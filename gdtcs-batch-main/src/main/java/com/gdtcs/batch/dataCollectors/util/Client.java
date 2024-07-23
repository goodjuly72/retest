package com.gdtcs.batch.dataCollectors.util;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import com.gdtcs.batch.dataCollectors.vo.xml.error.EnvelopeFalut;

import com.gdtcs.batch.dataCollectors.vo.xml.error.Fault;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Slf4j
@Component
public class Client {

	private static final String CONTENT_TYPE = "text/xml; charset=utf-8";
	private static final String ENCODING = "UTF-8";
	private static final int CONNECT_TIMEOUT = 5 * 60 * 1000; // 5 minutes
	private static final int SOCKET_TIMEOUT = 900 * 1000; // 15 minutes

	public static String doService(String serviceUrl, String requestXml) {

		HttpPost postMethod = new HttpPost(serviceUrl);
		postMethod.setHeader("Content-Type", CONTENT_TYPE);
		postMethod.setHeader("Connection", "close");

		String responseXml = null;
		try {
			// HttpClient 설정
			RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(Timeout.ofMilliseconds(CONNECT_TIMEOUT))
				.setResponseTimeout(Timeout.ofMilliseconds(SOCKET_TIMEOUT))
				.build();

			// Custom Retry strategy 설정
			HttpRequestRetryStrategy retryStrategy = new HttpRequestRetryStrategy() {
				@Override
				public boolean retryRequest(HttpRequest request, IOException exception, int execCount, HttpContext context) {
					return false;
				}

				@Override
				public boolean retryRequest(HttpResponse response, int execCount, HttpContext context) {
					return false;
				}

				@Override
				public TimeValue getRetryInterval(HttpRequest request, IOException exception, int execCount, HttpContext context) {
					return TimeValue.ZERO_MILLISECONDS;
				}

				@Override
				public TimeValue getRetryInterval(HttpResponse response, int execCount, HttpContext context) {
					return TimeValue.ZERO_MILLISECONDS;
				}
			};

			try (CloseableHttpClient client = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setRetryStrategy(retryStrategy)
				.build()) {

				StringEntity requestEntity = new StringEntity(requestXml, ContentType.create(CONTENT_TYPE, ENCODING));
				postMethod.setEntity(requestEntity);

				try (CloseableHttpResponse response = client.execute(postMethod)) {
					int responseCode = response.getCode();
					responseXml = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
					log.debug(">>>>>> responseCode : {}", responseCode);
					EntityUtils.consume(response.getEntity());

					if (responseCode == HttpStatus.SC_OK) {
						// 정상 응답인 경우
						log.info("Successful response received.");
					} else if (responseXml.contains("Fault>")) {
						// 서비스 오류가 발생한 경우
							try {
								JAXBContext jaxbContext = JAXBContext.newInstance(EnvelopeFalut.class);
								Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

								StringReader reader = new StringReader(responseXml);
								EnvelopeFalut envelope = (EnvelopeFalut) unmarshaller.unmarshal(reader);
								Fault fault = envelope.getBody().getFault();
								log.error(">>>>>> Fault Code: {}" , fault.getFaultCode());
								log.error(">>>>> Fault String: {}" , fault.getFaultString());
							} catch (JAXBException e) {
								log.error("Service error occurred: ", e);
								e.printStackTrace();
							}
					} else {
						// 통신 오류가 발생한 경우
						log.error("Communication error occurred: responseCode = {}, responseXml = {}", responseCode, responseXml);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error occurred during HTTP request: ", e);
		} finally {
			postMethod.reset();
		}

		return responseXml;
	}
}
