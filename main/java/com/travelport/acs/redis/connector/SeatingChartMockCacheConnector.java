/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * /**
 * RCB Mock Cache Connector Implementation for Unit Test Cases
 * This Can be Used to Initiate a Mock Cache with Predefined RCB Mock Values or An Empty Cache
 * also be initialized with No-Arg Constructor
 * @author Sandipan.Das
 *
 * @param <K> Cache Key Type
 * @param <V> Cache Value Type
 */
//TODO Commented as part of NTAC Model upgrade and unavailability of SeatingChart Object
public class SeatingChartMockCacheConnector {/*extends AbstractMockCacheConnector<String, SeatingChart> {
	
	*//**
	 * Default Constructor to Initiate a Blank Mock Cache.
	 * User Can first Insert and Then Delete from Mock Cache
	 *//*
	public SeatingChartMockCacheConnector() {
		super();
	}
	
	*//**
	 * Initialize a Mock Cache with Predefined Values from the File. File Content Should be Gson compatible
	 * File Should be in "mock-data" folder in classpath. Should not contain in "/" before or after.
	 * @param mockCacheFile FileName in "mock-data" folder of classpath
	 *//*
	public SeatingChartMockCacheConnector(String mockCacheFile) {
		super(mockCacheFile, new TypeToken<Map<String, SeatingChart>>() {}.getType());
	}
*/
	
}
