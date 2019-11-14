/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.util.List;
import java.util.Map;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;

/**
 * Abstract Redis Cache Connector to Connect Redis Cache With Any Key Value Pair Objects
 * 
 * @author Sandipan.das
 *
 */
public abstract class AbstractRedisCacheConnector<K,V> implements ICacheConnector<K, V> {
	
	private RedisCommands<K, V> connection;
	private RedisClient client;
	
	/**
	 * Abstract Method that should be implemented by the Extention Class to Connect 
	 * to the different Redis Provider (Different Provider has different ways to connect to Redis)
	 * This Method Should Return a RedisConnection which Will Be used to Connect Redis Cache
	 */
	protected abstract RedisCommands<K, V> connect();
	
	/**
	 * Redis Async Cache Connection Implementation
	 *
	 * @param <K> Key
	 * @param <V> Value
	 */
	private static class RedisAsync<K,V> implements Runnable {
		private Map<K, V> keyValuePair;
		private K key;
		private V value;
		private RedisCommands<K, V> cmds;
		
		/**
		 * Redis Cache Async Connection With Single Key Value Pair
		 * @param key Key
		 * @param value Value
		 * @param cmds Redis Command Interface
		 */
		public RedisAsync(K key, V value, RedisCommands<K, V> cmds) {
			this.value = value;
			this.cmds = cmds;
			this.key = key;
		}
		
		/**
		 * Redis Cache Async Connection With Multiple Key Value Pair
		 * @param keyValuePair Key Value Pair Map
		 * @param cmds Redis Command Interface
		 */
		public RedisAsync(Map<K, V> keyValuePair, RedisCommands<K, V> cmds) {
			this.keyValuePair = keyValuePair;
			this.cmds = cmds;
		}
		
		/*
		 * Insert Key Value Object in Redis Cache Async
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if(this.keyValuePair!=null){
				cmds.mset(this.keyValuePair);
			}else{
				cmds.set(this.key, this.value);
			}
		}
	}
	
	/*
	 * Get Value against a Key in Redis Cache
	 * @see com.travelport.acs.redis.connector.ICacheConnector#getValue(java.lang.Object)
	 */
	@Override
	public V getValue(K key) {
		if(getClient()==null || connection==null || !connection.isOpen()){
			connection = connect();
		}
		final V value = connection.get(key);
		connection.close();
		return value;
	}
	
	/*
	 * Save a Key Pair Value in Redis Cache (Synchronous Connection)
	 * @see com.travelport.acs.redis.connector.ICacheConnector#save(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean save(K key, V value) {
		if(getClient()==null || connection==null || !connection.isOpen()){
			connection = connect();
		}
		connection.set(key, value);
		connection.close();
		return true;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean expire(K key, long value) {
		if(getClient()==null || connection==null || !connection.isOpen()){
			connection = connect();
		}
		connection.expire(key, value);
		connection.close();
		return true;
	}
	
	/*
	 * Save a Key Pair Value in Redis Cache Asynchronously
	 * @see com.travelport.acs.redis.connector.ICacheConnector#saveAsync(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void saveAsync(K key, V value) {
		if(getClient()==null || connection==null || !connection.isOpen()){
			connection = connect();
		}
		new Thread(new RedisAsync<K,V>(key, value, connection)).start();
	}
	
	/*
	 * Save Bulk Key Pair Values in Redis Cache (Synchronous Connection)
	 * @see com.travelport.acs.redis.connector.ICacheConnector#saveAll(java.util.Map)
	 */
	@Override
	public boolean saveAll(Map<K, V> keyValuePair) {
		if(getClient()==null || connection==null || !connection.isOpen()){
			connection = connect();
		}
		connection.mset(keyValuePair);
		connection.close();
		return true; //As mset already returns 'OK'
	}
	
	/*
	 * Save Bulk Key Pair Values in Redis Cache Asynchronously
	 * @see com.travelport.acs.redis.connector.ICacheConnector#saveAllAsync(java.util.Map)
	 */
	@Override
	public void saveAllAsync(Map<K, V> keyValuePair) {
		if(getClient()==null || connection==null || !connection.isOpen()){
			connection = connect();
		}
		new Thread(new RedisAsync<K,V>(keyValuePair, connection)).start();
	}

	/*
	 * Get Bulk Values from Redis Cache Using Key Array
	 * @see com.travelport.acs.redis.connector.ICacheConnector#getAllValues(java.lang.Object[])
	 */
	@Override
	public List<V> getAllValues(K... keys) {
		if(getClient()==null || connection==null || !connection.isOpen()){
			connection = connect();
		}
		final List<V> values = connection.mget(keys);
		connection.close();
		return values;
	}
	
	/**
	 * @param connection
	 */
	public void setConnection(RedisCommands<K, V> connection) {
		this.connection = connection;
	}
	
	/**
	 * @param client
	 */
	public void setClient(RedisClient client) {
		this.client = client;
	}
	
	/**
	 * @return client
	 */
	public RedisClient getClient() {
		return client;
	}
	
}
