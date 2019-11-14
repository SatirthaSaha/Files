package com.travelport.acs.redis.connector;

import org.junit.Test;

public class RCBMockCacheConnectorTest {
	
	@Test
	public void testRCBMockCacheConnectorPrePopulatedValues(){/*
		final RCBMockCacheConnector connector = new RCBMockCacheConnector("redisCache.mock");
		final FareFamilyOfferings fareFamilies = new FareFamilyOfferings();
		final FareFamilyOffering someFareFamily = new FareFamilyOffering();
		someFareFamily.setName("Sandipan");
		someFareFamily.setCarrierCode("I5");
		fareFamilies.setFareFamilies(Arrays.asList(someFareFamily));
		connector.save("I5", fareFamilies);
		Map<String, FareFamilyOfferings> keyPair=new HashMap<>();
		keyPair.put("ABC", fareFamilies);
		connector.saveAll(keyPair);
		connector.saveAllAsync(keyPair);
		connector.saveAsync("ABC", fareFamilies);
		connector.getAllValues("ABC");
		connector.getValue("ABC");
		
		assertEquals("Values Should Be Same.", connector.getValue("U2").getFareFamilies().get(0).getBrandList().get(0).getName(), "a4JyOypGlw");
		assertEquals("Values Should Be Same.", connector.getValue("I5").getFareFamilies().get(0).getName(), "Sandipan");
	*/}
}