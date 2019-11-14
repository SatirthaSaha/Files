/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.PeriodType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.lambdaworks.redis.codec.RedisCodec;

/**
 * Implementation of Kryo Code for Serialization/De-Serialization
 * of Key/Value Object for Redis Cache
 * 
 * @author Sandipan.das
 *
 */
public class KryoSerializerCodec<K,V> implements RedisCodec<K, V> {
	
	private Class<K> keyType;
	private Class<V> valueType;
	
	/**
	 * Create Factory to Kryo Serializer
	 * Register localDate
	 * Register LocalTime
	 * Register PeriodType
	 * return kryo
	 */
	private KryoFactory factory = new KryoFactory() {
		public Kryo create () {
			final Kryo kryo = new Kryo();
			kryo.register(LocalDate.class, new JavaSerializer());
			kryo.register(LocalTime.class, new JavaSerializer());
			kryo.register(PeriodType.class, new JavaSerializer());
			return kryo;
		}
	}; 
	
	private final KryoPool pool = new KryoPool.Builder(factory).softReferences().build();
	
	/**
	 * Constructor to Set Key Value Type for Ser/De-Ser
	 * @param keyType Java Type of Key
	 * @param valueType Java Type of Value
	 */
	public KryoSerializerCodec(Class<K> keyType, Class<V> valueType){
		this.keyType = keyType;
		this.valueType = valueType;
	}
	
	
	
	
	/**
	 * Decode Bytes Obtained from Network Stream into Key Object
	 * @see com.lambdaworks.redis.codec.RedisCodec#decodeKey(java.nio.ByteBuffer)
	 * return key.
	 * add to byte array.
	 **/
	public K decodeKey(ByteBuffer byteBuffer) {
		final byte[] array = new byte[byteBuffer.remaining()];
		byteBuffer.get(array);
		final K key = pool.run(new KryoCallback<K>() {
			public K execute(Kryo kryo) {
				return kryo.readObject(new Input(array), keyType);
			}
		});
		return key;
	}
	
	/**
	 * Decode Bytes Obtained from Network Stream into Value Object
	 * @see com.lambdaworks.redis.codec.RedisCodec#decodeValue(java.nio.ByteBuffer)
	 * add to byte array
	 * return value
	 **/
	public V decodeValue(final ByteBuffer byteBuffer) {
		final byte[] array = new byte[byteBuffer.remaining()];
		byteBuffer.get(array);
		final V value = pool.run(new KryoCallback<V>() {
			public V execute(Kryo kryo) {
				return  kryo.readObject(new Input(array), valueType);
			}
		});
		return value;
	}
	
	/**
	 * Encode Bytes from Key Object for Network Transmission
	 * @see com.lambdaworks.redis.codec.RedisCodec#encodeKey(java.lang.Object)
	 * create new ByteArrayOutputStream
	 * Write to object.
	 * return output.getOutputStream()).toByteArray()
	 * convert to ByteArrayOutputStream
	 */
	public ByteBuffer encodeKey(K key) {
		final Output output = new Output(new ByteArrayOutputStream());
		final Kryo kryo = pool.borrow();
		kryo.writeObject(output, key);
		output.close();
		pool.release(kryo); 
		return ByteBuffer.wrap(((ByteArrayOutputStream) output.getOutputStream()).toByteArray());
	}
	
	/**
	 * Encode Bytes from Value Object for Network Transmission
	 * @see com.lambdaworks.redis.codec.RedisCodec#encodeValue(java.lang.Object)
	 **/
	public ByteBuffer encodeValue(V value) {
		final Output output = new Output(new ByteArrayOutputStream());
		final Kryo kryo = pool.borrow();
		kryo.writeObject(output, value);
		output.close();
		pool.release(kryo); 
		return ByteBuffer.wrap(((ByteArrayOutputStream) output.getOutputStream()).toByteArray());
 
	}
}
