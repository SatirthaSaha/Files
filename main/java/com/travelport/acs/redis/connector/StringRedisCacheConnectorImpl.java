/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import org.apache.commons.lang3.StringUtils;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.RedisCodec;
import com.travelport.acs.expframework.exceptions.ACSRuntimeException;
import com.travelport.acs.logger.impl.ACSLogger;
import com.travelport.acs.redis.connector.utils.RedisConnectorConfig;

/**
 * Implementation of StringRedisCacheConnectorImpl - Through this connector we can put or pull String values to redis cache - mostly would be used for Saving Rules Config. 
 * 
 * @author Sandipan.Das
 *
 */
public class StringRedisCacheConnectorImpl extends AbstractRedisCacheConnector<String, String> {
	
	private String redisDNSName;
	private String redisPort;
	private String redisCredential;
	private boolean sslEnabled;
	private RedisCodec<String, String> serializer;
	
	private static final ACSLogger LOGGER = ACSLogger.getLogger(RedisConnectorConfig.class);
	
	public StringRedisCacheConnectorImpl() {
		LOGGER.info("Constructor");
	}
	/*
	 * Connect to the Azure Redis Cache using DNS Name, Port and Credential
	 * @see com.travelport.acs.redis.connector.AbstractRedisCacheConnector#connect()
	 */
	@Override
	protected RedisCommands<String, String> connect() {
		LOGGER.debug("StringRedisCacheConnectorImpl RedisConnection");
		if(getClient()==null){
			if(StringUtils.isEmpty(this.redisDNSName) || StringUtils.isEmpty(this.redisPort)){
				throw new ACSRuntimeException("Redis DNS Name/Port is required to establish the connection.");
			}
			
			if(!StringUtils.isNumeric(this.redisPort)){
				throw new ACSRuntimeException("Redis Port is Invalid to establish the connection.");
			}
			LOGGER.debug("StringRedisCacheConnectorImpl RedisURI");
			RedisURI redisUri = null;
			if(StringUtils.isNotEmpty(this.redisCredential)){
				redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).withPassword(this.redisCredential).build();
			}else{
				redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).build();
			}
			setClient(RedisClient.create(redisUri));
		}
		if(serializer!=null){
			return getClient().connect(serializer).sync();
		}else{
			return getClient().connect(new JavaSerializerRedisCodec<String, String>()).sync();
		}
	}
	
	/**
	 * Setting Redis DNS Name
	 * @param redisDNSName
	 */
	public void setRedisDNSName(String redisDNSName) {
		this.redisDNSName = redisDNSName;
	}
	
	/**
	 * Setting Redis Port
	 * @param redisPort
	 */
	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}
	
	/**
	 * Setting REdis Credentials
	 * @param redisCredential
	 */
	public void setRedisCredential(String redisCredential) {
		this.redisCredential = redisCredential;
	}
	
	/**
	 * Setting SSL Enabled
	 * @param sslEnabled
	 */
	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	
	/**
	 * Setting serializer
	 * Input-RedisCodec serializer
	 * @param serializer
	 */
	public void setSerializer(RedisCodec<String, String> serializer) {
		this.serializer = serializer;
	}
}
