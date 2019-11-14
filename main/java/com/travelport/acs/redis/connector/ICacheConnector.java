/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.util.List;
import java.util.Map;
/**
 * Interface to Connect to Cache and Save/Retrieve data.
 * 
 * @author Sandipan.das
 * Interface
 * IcacheConnector
 * 
 */
public interface ICacheConnector<K,V> {
	 V getValue(K key);
	 boolean save(K key, V value);
	 void saveAsync(K key, V value);
	 boolean saveAll(Map<K,V> keyPair);
	 void saveAllAsync(Map<K,V> keyPair);
	 List<V> getAllValues(K...keys);
}
