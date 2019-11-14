/*
 * Copyright (c) 2017, Travelport.
 * All Rights Reserved.
 * Use is subject to license agreement.
 */
package com.travelport.acs.redis.connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;


import redis.embedded.RedisServer;
import uk.co.jemos.podam.api.PodamFactory;

//TODO Commented as part of NTAC Model upgrade and unavailability of SeatingChart Object
//Should Not Run Live in Unit Test Cases (Implementation using Live Connect to Redis for Test)
@Ignore
public class SeatingChartRedisCacheConnectorImplTest {
	RedisServer redisServer = null;
	
	@Before
	public void setup() throws IOException{
		redisServer = new RedisServer(6379);
		redisServer.start();
	}
	
	public void tearDown() throws InterruptedException{
		redisServer.stop();
	}

	/*@Test
	public void connectorLiveTest() {
		final SeatingChartRedisCacheConnectorImpl connector = prepareDefaultRedisCacheImpl();
		final PodamFactory factory = CustomPodamFactory.getCustomIntegerPodamImpl();
		final Map<String, SeatingChart> values = new HashMap<String, SeatingChart>();
		final SeatingChart value = factory.manufacturePojoWithFullData(SeatingChart.class);
		values.put("AK3201234", value);
			new Gson().toJson(values);
		System.out.println(new Gson().toJson(values));
		assertEquals("Save should success.", connector.save("AK3201234", value), true);;
		final SeatingChart retrivedValue = connector.getValue("AK3201234");
		assertNotNull(retrivedValue);
	}


	private SeatingChartRedisCacheConnectorImpl prepareDefaultRedisCacheImpl() {
		final SeatingChartRedisCacheConnectorImpl redisCache = new SeatingChartRedisCacheConnectorImpl();
		redisCache.setRedisDNSName("127.0.0.1");
		redisCache.setRedisPort("6379");
		redisCache.setSslEnabled(false);
		return redisCache;
	}*/
}
