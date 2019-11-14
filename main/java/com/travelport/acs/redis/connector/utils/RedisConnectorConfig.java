/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector.utils;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.acs.expframework.exceptions.ACSRuntimeException;
import com.travelport.acs.logger.impl.ACSLogger;
import com.travelport.acs.redis.connector.InputStreamRedisCacheConnectorImpl;
import com.travelport.acs.redis.connector.IntegerRedisCacheConnectorImpl;
import com.travelport.acs.redis.connector.KryoSerializerCodec;
import com.travelport.acs.redis.connector.RCBRedisCacheConnectorImpl;
import com.travelport.acs.redis.connector.StringRedisCacheConnectorImpl;
import com.travelport.model.brandedfares.FareFamilies;


public class RedisConnectorConfig {
	private static final String REDIS_PSSWORD = "com.travelport.acs.rcb.redis.service.password";
	private static final String REDIS_PORT = "com.travelport.acs.rcb.redis.service.port";
	private static final String REDIS_DNS_NAME = "com.travelport.acs.rcb.redis.service.dns";
	
	private static final String REDIS_DEFAULT_DNS_NAME = "tpaircomm-acs-dev.redis.cache.windows.net";
	private static final String REDIS_DEFAULT_PORT = "6380";
	private static final String REDIS_DEFAULT_PSSWORD = "dLMoTdtqpV2zjlZPdKyUzoBdRvUiyncNYB6vCU8H5lk=";
	
	private static final ACSLogger LOGGER = ACSLogger.getLogger(RedisConnectorConfig.class);
	
	RedisConnectorConfig() {
	}
	/**
	 * Prepare Redis Connector to Save & Retrieved <String, String> Key Value Pair
	 * @return StringRedisCacheConnectorImpl
	 */
	public static StringRedisCacheConnectorImpl prepareStringRedisCacheConnector() {
		parseRedisConfig();
		final StringRedisCacheConnectorImpl connector = new StringRedisCacheConnectorImpl();
		connector.setRedisDNSName(System.getProperty(REDIS_DNS_NAME));
		connector.setRedisPort(System.getProperty(REDIS_PORT));
		connector.setRedisCredential(System.getProperty(REDIS_PSSWORD));
		connector.setSslEnabled(true);
		return connector;
	}
	
	/**
	 * Prepare Redis Connector to Save & Retrieved <String, Integer> Key Value Pair
	 * @return IntegerRedisCacheConnectorImpl
	 */
	public static IntegerRedisCacheConnectorImpl prepareIntegerRedisCacheConnector() {
		parseRedisConfig();
		IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
		connector.setRedisDNSName(System.getProperty(REDIS_DNS_NAME));
		connector.setRedisPort(System.getProperty(REDIS_PORT));
		connector.setRedisCredential(System.getProperty(REDIS_PSSWORD));
		connector.setSslEnabled(true);
		return connector;
	}
	
	/**
	 * Prepare Redis Connector to Save & Retrieved <String, InputStream> Key Value Pair
	 * @return InputStreamRedisCacheConnectorImpl
	 */
	public static InputStreamRedisCacheConnectorImpl prepareInputStreamRedisCacheConnector() {
		parseRedisConfig();
		InputStreamRedisCacheConnectorImpl connector = new InputStreamRedisCacheConnectorImpl();
		connector.setRedisDNSName(System.getProperty(REDIS_DNS_NAME));
		connector.setRedisPort(System.getProperty(REDIS_PORT));
		connector.setRedisCredential(System.getProperty(REDIS_PSSWORD));
		connector.setSslEnabled(true);
		return connector;
	}
	
    public static void prepareRCBRedisCacheConnector (RCBRedisCacheConnectorImpl rcbRedisCacheConnectorImpl) {
        parseRedisConfig ();
        
        rcbRedisCacheConnectorImpl.setRedisDNSName (System.getProperty (REDIS_DNS_NAME));
        rcbRedisCacheConnectorImpl.setRedisPort (System.getProperty (REDIS_PORT));
        rcbRedisCacheConnectorImpl.setRedisCredential (System.getProperty (REDIS_PSSWORD));
        rcbRedisCacheConnectorImpl.setSslEnabled (true);
        rcbRedisCacheConnectorImpl.setSerializer (new KryoSerializerCodec<String, FareFamilies> (String.class, FareFamilies.class));
    }
	
	/**
	 * Parse Redis Cache Configuration from Stackato User Provided Service attached with the Service
	 */
	private static void parseRedisConfig() {
		String redisDNS = System.getProperty(REDIS_DNS_NAME);
		String redisPort = System.getProperty(REDIS_PORT);
		String redisPassword = System.getProperty(REDIS_PSSWORD);
		
		if(StringUtils.isEmpty(redisDNS) || StringUtils.isEmpty(redisPort) || StringUtils.isEmpty(redisPassword)){
			final String vcap = System.getenv("VCAP_SERVICES");
			if(vcap==null || StringUtils.isEmpty(vcap)){
				LOGGER.error("No VCAP Configuration found for Redis Cache Config. Populating Default Config for DEV Environment.");
				System.setProperty(REDIS_DNS_NAME, REDIS_DEFAULT_DNS_NAME);
				System.setProperty(REDIS_PORT, REDIS_DEFAULT_PORT);
				System.setProperty(REDIS_PSSWORD, REDIS_DEFAULT_PSSWORD);
			} else {
				LOGGER.debug("VCAP_SERVICES value is: "+vcap);
				
				final ObjectMapper mapper = new ObjectMapper();
				try {
					extractJSON(vcap, mapper);
					
				} catch (IOException ex) {
					LOGGER.error("VCAP Services Tree Read Error for Redis Cache Config!!!!!."+ex);
					throw new ACSRuntimeException("VCAP Services Tree Read Error for Redis Cache Config!!!!!.");
				}
			}
		}
	}
	
	/**
	 * 
	 * @param vcap
	 * @param mapper
	 * @throws IOException
	 */
	private static void extractJSON(final String vcap, final ObjectMapper mapper)
			throws IOException {
		String redisDNS;
		String redisPort;
		String redisPassword;
		JsonNode jn;
		jn = mapper.readTree(vcap);
		final JsonNode redisDNSJson = jn.findValue(REDIS_DNS_NAME);
		redisDNS = redisDNSJson == null ? null : redisDNSJson.asText();
		System.setProperty(REDIS_DNS_NAME, redisDNS);
		
		final JsonNode redisPortJson = jn.findValue(REDIS_PORT);
		redisPort = redisPortJson == null ? null : redisPortJson.asText();
		System.setProperty(REDIS_PORT, redisPort);
		
		final JsonNode redisPasswordJson = jn.findValue(REDIS_PSSWORD);
		redisPassword = redisPasswordJson == null ? null : redisPasswordJson.asText();
		System.setProperty(REDIS_PSSWORD, redisPassword);
	}
}
