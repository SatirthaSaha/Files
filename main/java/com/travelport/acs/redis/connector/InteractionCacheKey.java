/* Copyright (c) 2017 Travelport. All rights reserved. */
package com.travelport.acs.redis.connector;

/**
 * Composite Key to Save and Fetch Data from Interaction Manager
 * 
 * @author Sandipan.das
 *
 */
public class InteractionCacheKey {
	private String interactionId;
	private String key;
	
	/**
	 * @param interactionId
	 * @param key
	 */
	public InteractionCacheKey(String interactionId, String key){
		this.interactionId = interactionId;
		this.key = key;
	}
	
	/**
	 * @param interactionId
	 */
	public void setInteractionId(String interactionId) {
		this.interactionId = interactionId;
	}
	
	/**
	 * @return interactionId
	 */
	public String getInteractionId() {
		return interactionId;
	}
	
	/**
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @return key
	 */
	public String getKey() {
		return key;
	}
}
