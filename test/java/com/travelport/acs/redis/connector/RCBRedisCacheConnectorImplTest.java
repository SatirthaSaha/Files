/*
 * Copyright (c) 2017, Travelport.
 * All Rights Reserved.
 * Use is subject to license agreement.
 */
package com.travelport.acs.redis.connector;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



import redis.embedded.RedisServer;
import uk.co.jemos.podam.api.PodamFactory;

//Should Not Run Live in Unit Test Cases (Implementation using Live Connect to Redis for Test)
@Ignore
public class RCBRedisCacheConnectorImplTest {/*
	RedisServer redisServer = null;
	
	@Before
	public void setup() throws IOException{
		redisServer = new RedisServer(6379);
		redisServer.start();
	}
	
	@After
	public void tearDown() throws InterruptedException{
		redisServer.stop();
	}

	@Test
	public void testConnectorLiveTest() {
		final RCBRedisCacheConnectorImpl connector = prepareDefaultRedisCacheImpl();
		final PodamFactory factory = CustomPodamFactory.getCustomIntegerPodamImpl();
		final FareFamilyOffering value = factory.manufacturePojoWithFullData(FareFamilyOffering.class);
		final FareFamilyOfferings families = new FareFamilyOfferings();
		families.setFareFamilyOffering(fareFamilyOffering);
		families.setFareFamilyOffering((new ArrayList<FareFamilyOffering>(Arrays.asList(value));
		final FareFamilies retrivedValue = connector.getValue("DL");
		assertNotNull(retrivedValue);
	}

	@Test
	public void testSerializerCompareLiveTest() throws IOException {
		final int numberOfObject = 10;
		final FareFamilies[] fareFamilyArr = prepareFareFamilyArray(numberOfObject);

		final RCBRedisCacheConnectorImpl connectorJavaSer = prepareDefaultRedisCacheImpl();
		connectorJavaSer.setSerializer(new JavaSerializerRedisCodec<String, FareFamilies>());

		long javaSerTime = iterateAndCalcuateTime(connectorJavaSer, fareFamilyArr);
		assertTrue(javaSerTime>1);
		System.out.println("************************************** Time Taken To Save and Retrieve " + numberOfObject + " Objects Using Java Serializer: " + javaSerTime);

		System.gc();

		final RCBRedisCacheConnectorImpl connectorKryoSer = prepareDefaultRedisCacheImpl();
		connectorKryoSer.setSerializer(new KryoSerializerCodec<String, FareFamilies>(String.class, FareFamilies.class));

		long kryoSerTime = iterateAndCalcuateTime(connectorKryoSer, fareFamilyArr);
		assertTrue(kryoSerTime>1);
		System.out.println("************************************** Time Taken To Save and Retrieve " + numberOfObject + " Objects Using Kryo Serializer: " + kryoSerTime);

		System.gc();

		final RCBRedisCacheConnectorImpl connectorPhotoStuffSer = prepareDefaultRedisCacheImpl();
		connectorPhotoStuffSer.setSerializer(new ProtoStuffSerializerCodec<String, FareFamilies>(String.class, FareFamilies.class));

		long protoStuffSerTime = iterateAndCalcuateTime(connectorPhotoStuffSer, fareFamilyArr);
		assertTrue(protoStuffSerTime>1);
		System.out.println("************************************** Time Taken To Save and Retrieve " + numberOfObject + " Objects Using ProtoStuff Serializer: " + protoStuffSerTime);
	}

	private long iterateAndCalcuateTime(RCBRedisCacheConnectorImpl connector, FareFamilies[] fareFamilyArr) {
		final long time = System.currentTimeMillis();
		for (int i = 0; i < fareFamilyArr.length; i++) {
			connector.save(String.valueOf(i), fareFamilyArr[i]);
		}
		for (int i = 0; i < fareFamilyArr.length; i++) {
			assertNotNull(connector.getValue(String.valueOf(i)));
		}
		return System.currentTimeMillis() - time;
	}

	private RCBRedisCacheConnectorImpl prepareDefaultRedisCacheImpl() {
		final RCBRedisCacheConnectorImpl redisCache = RCBRedisCacheConnectorImpl.getInstance ();
		redisCache.setRedisDNSName("127.0.0.1");
		redisCache.setRedisPort("6379");
		redisCache.setSslEnabled(false);
		return redisCache;
	}

	private FareFamilies[] prepareFareFamilyArray(int count) {
		final PodamFactory factory = CustomPodamFactory.getCustomIntegerPodamImpl();
		final FareFamilies[] fareFamilyArr = new FareFamilies[count];
		for (int i = 0; i < count; i++) {
			FareFamily value = factory.manufacturePojoWithFullData(FareFamily.class);
			fareFamilyArr[i] = new FareFamilies();
			fareFamilyArr[i].setFareFamilies(new ArrayList<FareFamily>(Arrays.asList(value)));
		}
		return fareFamilyArr;
	}
*/}
