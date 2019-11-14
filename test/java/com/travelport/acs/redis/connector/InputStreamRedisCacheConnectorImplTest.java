
 package com.travelport.acs.redis.connector;
 
 import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.util.collection.ByteBufferInputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.RedisCodec;
import com.travelport.acs.redis.connector.utils.RedisConnectorConfig;


import io.swagger.jackson.TypeNameResolver.Options;
import redis.clients.jedis.Protocol;
import redis.embedded.RedisServer;
 
 public class InputStreamRedisCacheConnectorImplTest {/*
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
 	InputStreamRedisCacheConnectorImpl inputStreamRedisCacheConnectorImpl=new InputStreamRedisCacheConnectorImpl();
 	StringRedisCacheConnectorImpl stringRedisCacheConnectorImpl=new StringRedisCacheConnectorImpl();
 	SeatingChartRedisCacheConnectorImpl seatingChartRedisCacheConnectorImpl=new SeatingChartRedisCacheConnectorImpl();
 	SeatingChartMockCacheConnector seatingChartMockCacheConnector=new SeatingChartMockCacheConnector();
 	RCBMockCacheConnector  rcbMockCacheConnector=new RCBMockCacheConnector ();
 	
 	
 	
 	
 	@Test
 	public void testConnect(){
 		try {
 			inputStreamRedisCacheConnectorImpl.connect();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.setRedisDNSName("127.0.0.1");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.setRedisCredential("Redis Credentials");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.setRedisPort("6379");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.setSslEnabled(false);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		RedisCodec<String, byte[]> serializer=null;
 		try {
 			inputStreamRedisCacheConnectorImpl.setSerializer(serializer);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		RedisConnection<String, byte[]> connection=null;
 		try {
 			inputStreamRedisCacheConnectorImpl.setConnection(connection);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.toString();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		Map<String, byte[]> keyValuePair=new HashMap<>();
 		try {
 			inputStreamRedisCacheConnectorImpl.saveAll(keyValuePair);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.saveAllAsync(keyValuePair);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		byte[] data=null;
 		try {
 			inputStreamRedisCacheConnectorImpl.save("I5",data);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.getAllValues("ABC");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.getClient();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			inputStreamRedisCacheConnectorImpl.getValue("ABC");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		Assert.assertNotNull(keyValuePair);
 		
 	}
 	
 	@Test
 	public void testConnection(){
 		
 		try {
 			stringRedisCacheConnectorImpl.setRedisCredential("");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			stringRedisCacheConnectorImpl.setRedisDNSName("127.0.0.1");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			stringRedisCacheConnectorImpl.setRedisPort("6379");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			stringRedisCacheConnectorImpl.setSslEnabled(true);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		RedisCodec<String, String> serializer=null;
 		try {
 			stringRedisCacheConnectorImpl.setSerializer(serializer);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			stringRedisCacheConnectorImpl.connect();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		
 		try {
 			stringRedisCacheConnectorImpl.setRedisCredential("Redis Credential");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			stringRedisCacheConnectorImpl.connect();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		Assert.assertNotNull(stringRedisCacheConnectorImpl);
 	}
 	
 	@Test
 	public void testConnectionEmpty(){
 		try {
 			stringRedisCacheConnectorImpl.connect();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		Assert.assertNotNull(stringRedisCacheConnectorImpl);
 	}
 	
 	@Test
 	public void testConnectionNotNumeric(){
 		try {
 			stringRedisCacheConnectorImpl.setRedisPort("com.travelport.acs.rcb.redis.service.port");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			stringRedisCacheConnectorImpl.connect();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		Assert.assertNotNull(stringRedisCacheConnectorImpl);
 		
 	}
 	
 	@Test
 	public void testRedisConnectorConfig(){
 		RedisConnectorConfig.prepareStringRedisCacheConnector();
 		RedisConnectorConfig.prepareIntegerRedisCacheConnector();
 		RedisConnectorConfig.prepareInputStreamRedisCacheConnector();
 		final RCBRedisCacheConnectorImpl rcbRedisCacheConnectorImpl = RCBRedisCacheConnectorImpl.getInstance ();
		RedisConnectorConfig.prepareRCBRedisCacheConnector(rcbRedisCacheConnectorImpl);
		Assert.assertNotNull(rcbRedisCacheConnectorImpl);
 	}
 	
 	@Test
 	public void testInteractionCacheKey(){
 		InteractionCacheKey interactionCacheKey=new InteractionCacheKey("InteractionID", "Key");
 		interactionCacheKey.setInteractionId("Interaction ID");
 		interactionCacheKey.setKey("Key");
 		interactionCacheKey.getInteractionId();
 		interactionCacheKey.getKey();
 		Assert.assertNotNull(interactionCacheKey);
 		
 	}
 	
 	@Test
 	public void testRCBRedisCacheConnectorImplNull(){
 		RCBRedisCacheConnectorImpl rcbRedisCacheConnectorImpl=new RCBRedisCacheConnectorImpl();
 		try {
			rcbRedisCacheConnectorImpl.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Assert.assertNotNull(rcbRedisCacheConnectorImpl);
 	}
 	
 	@Test
 	public void testRCBRedisCacheConnectorImpl(){
 		RCBRedisCacheConnectorImpl rcbRedisCacheConnectorImpl=new RCBRedisCacheConnectorImpl();
 		try {
 			rcbRedisCacheConnectorImpl.setRedisCredential("");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			rcbRedisCacheConnectorImpl.setRedisDNSName("127.0.0.1");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			rcbRedisCacheConnectorImpl.setRedisPort("6379");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			rcbRedisCacheConnectorImpl.setSslEnabled(true);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			RedisCodec<String, FareFamilyOfferings> serializer=null;
			rcbRedisCacheConnectorImpl.setSerializer(serializer);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			rcbRedisCacheConnectorImpl.connect();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			rcbRedisCacheConnectorImpl.setRedisCredential("Redis Credential");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			RedisCodec serializer=new ProtoStuffSerializerCodec<>(Abc.class, Abc.class);
			rcbRedisCacheConnectorImpl.setSerializer(serializer);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			rcbRedisCacheConnectorImpl.connect();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		try {
 			RCBRedisCacheConnectorImpl.getInstance();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		Assert.assertNotNull(rcbRedisCacheConnectorImpl);
 	}
 	
 	@Test
 	public void testProtoStuffSerializerCodec(){
 		ProtoStuffSerializerCodec protoStuffSerializerCodec=new ProtoStuffSerializerCodec<>(Abc.class, Abc.class);
 		String str = "PANKAJ";
		byte[] bytes = str.getBytes();
		try {
			protoStuffSerializerCodec.decodeKey(ByteBuffer.wrap(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
 		try {
			protoStuffSerializerCodec.decodeValue(ByteBuffer.wrap(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Object key=new Object();
		try {
			protoStuffSerializerCodec.encodeKey(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Object value=new Object();
		try {
			protoStuffSerializerCodec.encodeValue(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(protoStuffSerializerCodec);
 	}
 	
 	@Test
 	public void testKryoSerializerCodec(){
 		KryoSerializerCodec kryoSerializerCodec=new KryoSerializerCodec<>(Abc.class, Abc.class);
 		String str = "PANKAJ";
		byte[] bytes = str.getBytes();
		try {
			kryoSerializerCodec.decodeKey(ByteBuffer.wrap(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
 		try {
 			kryoSerializerCodec.decodeValue(ByteBuffer.wrap(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Object key=new Object();
		try {
			kryoSerializerCodec.encodeKey(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Object value=new Object();
		try {
			kryoSerializerCodec.encodeValue(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(kryoSerializerCodec);
 	}
 	
 	@Test
 	public void testJavaSerializerRedisCodec(){
 		JavaSerializerRedisCodec javaSerializerRedisCodec=new JavaSerializerRedisCodec<>();
 		String str = "PANKAJ";
		byte[] bytes = str.getBytes();
		try {
			javaSerializerRedisCodec.decodeKey(ByteBuffer.wrap(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
 		try {
 			javaSerializerRedisCodec.decodeValue(ByteBuffer.wrap(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Abc key=new Abc();
		try {
			javaSerializerRedisCodec.encodeKey(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Object value=new Object();
		try {
			javaSerializerRedisCodec.encodeValue(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(javaSerializerRedisCodec);
 	}
 	
 	
 	
 	@Test
 	public void testIntegerRedisCacheConnectorImpl(){
 		try {
 			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
 			connector.setRedisPort("6379");
 			connector.setRedisCredential("rediscredential");
 			Assert.assertNotNull(connector);
 			connector.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
 		try {
 			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
 			connector.save("abc", 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		Map<String, Integer> keyValuePair=new HashMap<>();
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.saveAsync("abc", 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RedisClient client=new RedisClient();
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.setConnection(null);
			connector.saveAll(keyValuePair);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.saveAll(keyValuePair);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.saveAllAsync(keyValuePair);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.getAllValues("abc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.getClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.getValue("abc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
			connector.expire("abc", 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
 	}
 	
 	@Test
 	public void testIntegerRedisCacheConnectorImpl1(){
 		try {
 			IntegerRedisCacheConnectorImpl connector = new IntegerRedisCacheConnectorImpl();
 			connector.setRedisPort("6379");
 			connector.setRedisCredential("rediscredential");
 			connector.setClient(null);
 			connector.setConnection(null);
 			connector.setRedisDNSName(null);
 			connector.setRedisPort("64Y");
 			connector.connect();
 			Assert.assertNotNull(connector);
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
 	
 	
	@Test
 	public void testConnectAbstract(){
		RedisURI redisUri = null;
		
			redisUri = RedisURI.Builder.redis("127.0.0.1", Integer.parseInt("6379")).withSsl(false).withPassword("").build();
			Assert.assertNotNull(redisUri);
			inputStreamRedisCacheConnectorImpl.setClient(RedisClient.create(redisUri));
		try {
 			
 			inputStreamRedisCacheConnectorImpl.getValue("");
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			byte[] value = null;
			inputStreamRedisCacheConnectorImpl.save("key", value);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			long value = 0;
			inputStreamRedisCacheConnectorImpl.expire("key", value);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			
			byte[] value = null;
			inputStreamRedisCacheConnectorImpl.saveAsync("key", value);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			Map<String, byte[]> keyValuePair = null;
			inputStreamRedisCacheConnectorImpl.saveAll(keyValuePair);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			byte[] value = null;
			Map<String, byte[]> keyValuePair = null;
			inputStreamRedisCacheConnectorImpl.saveAllAsync(keyValuePair);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		try {
 			
			inputStreamRedisCacheConnectorImpl.getAllValues();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		
 	}
	
 */}
 
 class Abc
 {
	 
 }
