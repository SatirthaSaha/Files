/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import com.lambdaworks.redis.codec.RedisCodec;
import com.travelport.acs.logger.impl.ACSLogger;

/**
 * Implementation Using Java Serialization/De-Serialization
 * of Key/Value Object for Redis Cache
 * 
 * @author Sandipan.das
 *
 */
public class JavaSerializerRedisCodec<K,V> implements RedisCodec<K, V> {
	private static final ACSLogger LOGGER = ACSLogger.getLogger(JavaSerializerRedisCodec.class);
	/*
	 * Decode Bytes Obtained from Network Stream into Key Object
	 * @see com.lambdaworks.redis.codec.RedisCodec#decodeKey(java.nio.ByteBuffer)
	 * return key
	 * create an object to read in.
	 * Return null, if nothing found.
	 */
	
	public K decodeKey(ByteBuffer byteBuffer) {
		final byte[] bytes = new byte[byteBuffer.remaining()];
		byteBuffer.get(bytes);
		final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			final Object key = in.readObject();
			return (K) key;
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error(e);
		} 
		return null;
	}
	
	/*
	 * Decode Bytes Obtained from Network Stream into Value Object
	 * @see com.lambdaworks.redis.codec.RedisCodec#decodeValue(java.nio.ByteBuffer)
	 * return key
	 * create an object to read in.
	 * Return null, if nothing found.
	 */
	
	public V decodeValue(final ByteBuffer byteBuffer) {
		final byte[] bytes = new byte[byteBuffer.remaining()];
		byteBuffer.get(bytes);
		final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			final Object value = in.readObject();
			return (V) value;
		} catch (ClassNotFoundException | IOException e) {
			LOGGER.error(e);
		} 
		return null;
	}
	
	/*
	 * Encode Bytes from Key Object for Network Transmission
	 * @see com.lambdaworks.redis.codec.RedisCodec#encodeKey(java.lang.Object)
	 * return key
	 * create an object to read in.
	 * Return null, if nothing found.
	 */
	public ByteBuffer encodeKey(K key) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(key);
			out.flush();
			return ByteBuffer.wrap(bos.toByteArray());
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return ByteBuffer.wrap(bos.toByteArray());
		
	}
	
	/*
	 * Encode Bytes from Value Object for Network Transmission
	 * @see com.lambdaworks.redis.codec.RedisCodec#encodeValue(java.lang.Object)
	 * return key
	 * create an object to read in.
	 * Return null, if nothing found.
	 * Return an empty array.
	 
	 */
	
	public ByteBuffer encodeValue(V value) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(value);
			out.flush();
			return ByteBuffer.wrap(bos.toByteArray());
		} catch (IOException e) {
			LOGGER.error(e);
		} 

		return ByteBuffer.wrap(bos.toByteArray());

	}
}
