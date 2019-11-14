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
import com.travelport.model.brandedfares.FareFamilies;


/**
 * Implementation of RCB Caching Through Redis Cache for Branding Information
 * 
 * @author Sandipan.das
 *
 */
public class RCBRedisCacheConnectorImpl extends AbstractRedisCacheConnector<String, FareFamilies> {
	
	private String redisDNSName;
	private String redisPort;
	private String redisCredential;
	private boolean sslEnabled;
	private RedisCodec<String, FareFamilies> serializer;
	private static final ACSLogger LOGGER = ACSLogger.getLogger(RCBRedisCacheConnectorImpl.class);
    RCBRedisCacheConnectorImpl () {

    }
    
	/*
	 * Connect to the Azure Redis Cache using DNS Name, Port and Credential
	 * @see com.travelport.acs.redis.connector.AbstractRedisCacheConnector#connect()
	 * Check if serializer is null, return getClient.connect.
	 * Check if redisCredential is not null
	 * Check if redisDNSName is empty
	 * Check if redis port is empty
	 * Create a connection if the redisCredential is not empty
	 */
	
	protected RedisCommands<String, FareFamilies> connect() {
		LOGGER.debug("RCBRedisCacheConnectorImpl RedisConnection");
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
				LOGGER.debug("RCBRedisCacheConnectorImpl redisUri"+redisUri);
			}else{
				redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).build();
			}
			setClient(RedisClient.create(redisUri));
		}
		if(serializer!=null){
			return getClient().connect(serializer).sync();
		}else{
			return getClient().connect(new ProtoStuffSerializerCodec<>(String.class, FareFamilies.class)).sync();
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
	 * Setting Redis port
	 * @param redisPort
	 */
	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}
	
	/**
	 * Setting Redis Credentials
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
	 * Setting Serializer
	 * @param serializer
	 */
	public void setSerializer(RedisCodec<String, FareFamilies> serializer) {
		this.serializer = serializer;
	}
	
	
	/**
	 * Creating an instance of the Class
	 * @return redisCacheConnectorImpl
	 */
	public static RCBRedisCacheConnectorImpl getInstance(){
	    RCBRedisCacheConnectorImpl redisCacheConnectorImpl = new RCBRedisCacheConnectorImpl();
	    
	    RedisConnectorConfig.prepareRCBRedisCacheConnector (redisCacheConnectorImpl );
	    
	    return redisCacheConnectorImpl;
	}
	
}
