/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import org.apache.commons.lang3.StringUtils;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.RedisCodec;
import com.travelport.acs.expframework.exceptions.ACSRuntimeException;
import com.travelport.acs.logger.impl.ACSLogger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * Implementation of IntegerRedisCacheConnectorImpl - Through this connector we can put or pull Integer values to redis cache - mostly would be used for saving versions etc. 
 * 
 * @author Debabrata.Dey
 *
 */
	
public class IntegerRedisCacheConnectorImpl extends AbstractRedisCacheConnector<String, Integer> {
	private static final String REDIS_PSSWORDS = "com.travelport.acs.rcb.redis.service.password";
	private static final String REDIS_PORT = "com.travelport.acs.rcb.redis.service.port";
	private static final String REDIS_DNS_NAME = "com.travelport.acs.rcb.redis.service.dns";
	
	private String redisDNSName;
	private String redisPort;
	private String redisCredential;
	private boolean sslEnabled;
	private RedisCodec<String, Integer> serializer;
	private static JedisPool pool;
	private static final ACSLogger LOGGER = ACSLogger.getLogger(IntegerRedisCacheConnectorImpl.class);
	
	public IntegerRedisCacheConnectorImpl() {
		LOGGER.info("Constructor");
	}
	static {
		pool = new JedisPool(new JedisPoolConfig(), System.getProperty(REDIS_DNS_NAME),
				Integer.parseInt(System.getProperty(REDIS_PORT)), Protocol.DEFAULT_TIMEOUT,
				System.getProperty(REDIS_PSSWORDS), true);
	}
	
	/*
	 * Connect to the Azure Redis Cache using DNS Name, Port and Credential
	 * @see com.travelport.acs.redis.connector.AbstractRedisCacheConnector#connect()
	 */
	@Override
	protected RedisCommands<String, Integer> connect() {
		if(StringUtils.isEmpty(this.redisDNSName) || StringUtils.isEmpty(this.redisPort)){
			throw new ACSRuntimeException("Redis DNS Name/Port is required to establish the connection.");
		}
		
		if(!StringUtils.isNumeric(this.redisPort)){
			throw new ACSRuntimeException("Redis Port is Invalid to establish the connection.");
		}
		RedisURI redisUri = null;
		if(StringUtils.isNotEmpty(this.redisCredential)){
			redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).withPassword(this.redisCredential).build();
		}else{
			redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).build();
		}
		final RedisClient client = RedisClient.create(redisUri);
		if(serializer!=null){
			return client.connect(serializer).sync();
		}else{
			return client.connect(new JavaSerializerRedisCodec<String, Integer>()).sync();
		}
	}
	
	/* Connect to the Azure Redis Cache using DNS Name, Port and Credential
	 * @see com.travelport.acs.redis.connector.AbstractRedisCacheConnector#getValue(java.lang.Object)
	 */
	@Override
	public Integer getValue(String key) {
		if (pool != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return Integer.parseInt(jedis.get(key));
			} catch (NumberFormatException ex) {
				LOGGER.error("Unable to fetch data from redis for key : " + key, ex);
			} finally {
				if(jedis!=null){
					jedis.close();
				}
			}
		}
		return null;
	}
	
	/* Save the details
	 * @see com.travelport.acs.redis.connector.AbstractRedisCacheConnector#save(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean save(String key, Integer value) {
		if (pool != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				jedis.set(key, value.toString());
			} catch (NullPointerException ex) {
				LOGGER.error("Unable to save data into redis for key : " + key, ex);
			} finally {
				if(jedis!=null){
					jedis.close();
				}
			}
		}
		return true;
	}
	
	/**
	 * @param redisDNSName
	 */
	public void setRedisDNSName(String redisDNSName) {
		this.redisDNSName = redisDNSName;
	}
	
	/**
	 * @param redisPort
	 */
	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}
	
	/**
	 * @param redisCredential
	 */
	public void setRedisCredential(String redisCredential) {
		this.redisCredential = redisCredential;
	}
	
	/**
	 * @param sslEnabled
	 */
	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	
	/**
	 * @param serializer
	 */
	public void setSerializer(RedisCodec<String, Integer> serializer) {
		this.serializer = serializer;
	}
}
