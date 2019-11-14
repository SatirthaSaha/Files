/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.travelport.acs.expframework.exceptions.ACSRuntimeException;
import com.travelport.acs.logger.impl.ACSLogger;
/**
 * Mock Cache Connector to Mock Cache Data (Generic Implementation) in Unit Test Cases
 * This can be used to Mock any Type of Caching for Unit Test Cases
 * @author Sandipan.Das
 *
 * @param <K> Key Type
 * @param <V> Value Type
 */
public abstract class AbstractMockCacheConnector<K,V> implements ICacheConnector<K, V>{
	
	private Gson gson;
	private Map<K,V> mockCache;
	private static final ACSLogger LOGGER = ACSLogger.getLogger(AbstractMockCacheConnector.class);
	
	/**
	 * Default Constructor to Initialize MockCache Connector with Empty Mock Cache
	 * @param mockCacheFile File Name Under "mock-data" folder
	 */
	public AbstractMockCacheConnector() {
		mockCache = new HashMap<>();
	}
	
	/**
	 * Constructor to Initialize Mock Cache Connector with Provided Mock Data
	 * @param mockCacheFile File Name Under "mock-data" folder
	 * @param type java.reflect.Type for Absolute Cast
	 */
	public AbstractMockCacheConnector(String mockCacheFile, Type type) {
		try{
			gson = new Gson();
			final InputStream mockCacheInputStream = Thread.currentThread().getContextClassLoader().getResource("mock-data/" + mockCacheFile).openStream();
			final StringWriter writer = new StringWriter();
			IOUtils.copy(mockCacheInputStream, writer);
			mockCache = gson.fromJson(writer.toString(), type);
		}catch(IOException e){
			LOGGER.error("Error"+e);
			throw new ACSRuntimeException("Can't Parse the Mock Cache File from " + mockCacheFile);
		}
		
	}
	
	/*
	 * Get Value from Mock Cache
	 * @see com.travelport.acs.redis.connector.ICacheConnector#getValue(java.lang.Object)
	 */
	@Override
	public V getValue(K key) {
		return mockCache.get(key);
	}
	
	/*
	 * Save Key Value Pair in Mock Cache
	 * @see com.travelport.acs.redis.connector.ICacheConnector#save(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean save(K key, V value) {
		mockCache.put(key, value);
		return true;
	}
	
	/*
	 * Save Key Value Pair in Mock Cache (Async Communication Not Implemented)
	 * @see com.travelport.acs.redis.connector.ICacheConnector#saveAsync(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void saveAsync(K key, V value) {
		mockCache.put(key, value);
	}

	/*
	 * Save Bulk Key Value Pairs in Mock Cache
	 * @see com.travelport.acs.redis.connector.ICacheConnector#saveAll(java.util.Map)
	 */
	@Override
	public boolean saveAll(Map<K, V> keyPair) {
		mockCache.putAll(keyPair);
		return true;
	}
	
	/*
	 * Save Bulk Key Value Pairs in Mock Cache (Async Communication Not Implemented)
	 * @see com.travelport.acs.redis.connector.ICacheConnector#saveAllAsync(java.util.Map)
	 */
	@Override
	public void saveAllAsync(Map<K, V> keyPair) {
		mockCache.putAll(keyPair);
	}
	
	/*
	 * Retrieve Bulk Values using Keys Array
	 * @see com.travelport.acs.redis.connector.ICacheConnector#getAllValues(java.lang.Object[])
	 */
	@Override
	public List<V> getAllValues(K... keys) {
		final List<V> returnedItems = new ArrayList<>();
		for(final K key: keys){
			returnedItems.add(mockCache.get(key));
		}
		return returnedItems;
	}
}
