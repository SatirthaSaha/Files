/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.io.BufferedInputStream;

import org.apache.commons.lang3.StringUtils;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.RedisCodec;
import com.travelport.acs.expframework.exceptions.ACSRuntimeException;
import com.travelport.acs.logger.impl.ACSLogger;

/**
 * Implementation of InputStreamRedisCacheConnectorImpl - Through this connector we can put or pull XML or XL data from redis cache using BufferedInputStream
 * 
 * @author Debabrata.Dey
 *
 */
public class InputStreamRedisCacheConnectorImpl extends AbstractRedisCacheConnector<String, byte[]> {
	
	private String redisDNSName;
	private String redisPort;
	private String redisCredential;
	private boolean sslEnabled;
	private RedisCodec<String, byte[]> serializer;
	private static final ACSLogger LOGGER = ACSLogger.getLogger(InputStreamRedisCacheConnectorImpl.class);
	
	public InputStreamRedisCacheConnectorImpl() {
		LOGGER.info("Constructor");
	}
	
	/*
	 * Connect to the Azure Redis Cache using DNS Name, Port and Credential
	 * @see com.travelport.acs.redis.connector.AbstractRedisCacheConnector#connect()
	 */
	@Override
	protected RedisCommands<String, byte[]> connect() {
		LOGGER.debug("InputStreamRedisCacheConnectorImpl RedisConnection");
		if(getClient()==null){
			if(StringUtils.isEmpty(this.redisDNSName) || StringUtils.isEmpty(this.redisPort)){
				throw new ACSRuntimeException("Redis DNS Name/Port is required to establish the connection.");
			}
			
			if(!StringUtils.isNumeric(this.redisPort)){
				throw new ACSRuntimeException("Redis Port is Invalid to establish the connection.");
			}
			RedisURI redisUri = null;
			if(StringUtils.isNotEmpty(this.redisCredential)){
				redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).withPassword(this.redisCredential).build();
				LOGGER.debug("InputStreamRedisCacheConnectorImpl redisUri"+redisUri);
			}else{
				redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).build();
			}
			setClient(RedisClient.create(redisUri));
		}
		if(serializer!=null){
			return getClient().connect(serializer).sync();
		}else{
			return getClient().connect(new JavaSerializerRedisCodec<String, byte[]>()).sync();
		}
	}
	
	/**
	 * 
	 * @param redisDNSName
	 */
	public void setRedisDNSName(String redisDNSName) {
		this.redisDNSName = redisDNSName;
	}
	
	/**
	 * 
	 * @param redisPort
	 */
	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}
	
	/**
	 * 
	 * @param redisCredential
	 */
	public void setRedisCredential(String redisCredential) {
		this.redisCredential = redisCredential;
	}
	
	/**
	 * 
	 * @param sslEnabled
	 */
	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	
	/**
	 * 
	 * @param serializer
	 */
	public void setSerializer(RedisCodec<String, byte[]> serializer) {
		this.serializer = serializer;
	}
}
