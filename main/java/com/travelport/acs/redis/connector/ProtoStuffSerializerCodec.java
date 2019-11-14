/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.lambdaworks.redis.codec.RedisCodec;
import com.travelport.acs.logger.impl.ACSLogger;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Implementation of ProtoStuff Serializar/De-Serializer
 * of Key/Value Object for Redis Cache
 * 
 * @author Sandipan.das
 *
 */
public class ProtoStuffSerializerCodec<K,V> implements RedisCodec<K, V> {
	
	private Schema<K> keySchema;
	private Schema<V> valueSchema;
	private static final ACSLogger LOGGER = ACSLogger.getLogger(JavaSerializerRedisCodec.class);
	/**
	 * Constructor for Key Type and Value Type
	 * @param keyType Type for Key Object
	 * @param valueType Type for Value Object
	 */
	public ProtoStuffSerializerCodec(Class<K> keyType, Class<V> valueType){
		keySchema = RuntimeSchema.getSchema(keyType);
		valueSchema = RuntimeSchema.getSchema(valueType);
	}
	
	/*
	 * Decode Bytes Obtained from Network Stream into Key Object
	 * @see com.lambdaworks.redis.codec.RedisCodec#decodeKey(java.nio.ByteBuffer)
	 * return key
	 */
	public K decodeKey(ByteBuffer byteBuffer) {
		final byte[] array = new byte[byteBuffer.remaining()];
		byteBuffer.get(array);
		final K key = keySchema.newMessage();
        ProtobufIOUtil.mergeFrom(array, key, keySchema);
        return key;
	}
	
	/*
	 * Decode Bytes Obtained from Network Stream into Value Object
	 * @see com.lambdaworks.redis.codec.RedisCodec#decodeValue(java.nio.ByteBuffer)
	 * return value
	 */
	public V decodeValue(final ByteBuffer byteBuffer) {
		final byte[] array = new byte[byteBuffer.remaining()];
		byteBuffer.get(array);
		final V value = valueSchema.newMessage();
        ProtobufIOUtil.mergeFrom(array, value, valueSchema);
        return value;
	}
	
	/*
	 * Encode Bytes from Key Object for Network Transmission
	 * @see com.lambdaworks.redis.codec.RedisCodec#encodeKey(java.lang.Object)
	 * return out
	 * convert to byte array
	 * Write to key object
	 */
	public ByteBuffer encodeKey(K key) {
		final LinkedBuffer buffer = LinkedBuffer.allocate();
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ProtobufIOUtil.writeTo(out, key, keySchema, buffer);
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return ByteBuffer.wrap(out.toByteArray());
	}
	
	/*
	 * Encode Bytes from Value Object for Network Transmission
	 * @see com.lambdaworks.redis.codec.RedisCodec#encodeValue(java.lang.Object)
	 * return out
	 * convert to byte array
	 */
	public ByteBuffer encodeValue(V value) {
		final LinkedBuffer buffer = LinkedBuffer.allocate();
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ProtobufIOUtil.writeTo(out, value, valueSchema, buffer);
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return ByteBuffer.wrap(out.toByteArray());
	}
}
