/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

/**
 * Implementation of RCB Caching Through Redis Cache for Branding Information
 * 
 * @author Sandipan.das
 *
 */
//TODO Commented as part of NTAC Model upgrade and unavailability of SeatingChart Object
public class SeatingChartRedisCacheConnectorImpl{ /*extends AbstractRedisCacheConnector<String, SeatingChart> {
	
	private String redisDNSName;
	private String redisPort;
	private String redisCredential;
	private boolean sslEnabled;
	private RedisCodec<String, SeatingChart> serializer;
	
	
	 * Connect to the Azure Redis Cache using DNS Name, Port and Credential
	 * @see com.travelport.acs.redis.connector.AbstractRedisCacheConnector#connect()
	 
	@Override
	protected RedisConnection<String, SeatingChart> connect() {
		if(getClient()==null){
			if(StringUtils.isEmpty(this.redisDNSName) || StringUtils.isEmpty(this.redisPort)){
				throw new RuntimeException("Redis DNS Name/Port is required to establish the connection.");
			}
			
			if(!StringUtils.isNumeric(this.redisPort)){
				throw new RuntimeException("Redis Port is Invalid to establish the connection.");
			}
			RedisURI redisUri = null;
			if(StringUtils.isNotEmpty(this.redisCredential)){
				redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).withPassword(this.redisCredential).build();
			}else{
				redisUri = RedisURI.Builder.redis(this.redisDNSName, Integer.parseInt(this.redisPort)).withSsl(this.sslEnabled).build();
			}
			setClient(RedisClient.create(redisUri));
		}
		if(serializer!=null){
			return getClient().connect(serializer);
		}else{
			return getClient().connect(new ProtoStuffSerializerCodec<>(String.class, SeatingChart.class));
		}
	}
	
	public void setRedisDNSName(String redisDNSName) {
		this.redisDNSName = redisDNSName;
	}
	
	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}
	
	public void setRedisCredential(String redisCredential) {
		this.redisCredential = redisCredential;
	}
	
	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	
	public void setSerializer(RedisCodec<String, SeatingChart> serializer) {
		this.serializer = serializer;
	}
*/}
