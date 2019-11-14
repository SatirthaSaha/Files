package com.travelport.acs.redis.connector;

import java.sql.Timestamp;

import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.PodamUtils;
import uk.co.jemos.podam.typeManufacturers.IntTypeManufacturerImpl;

public class CustomPodamFactory {
	
	public static PodamFactory getCustomIntegerPodamImpl() {
        final IntTypeManufacturerImpl manufacturer = new IntTypeManufacturerImpl() {
            @Override
            public Integer getInteger(AttributeMetadata attributeMetadata) {

                if (attributeMetadata.getPojoClass() == Timestamp.class) {
                    return PodamUtils.getIntegerInRange(0, 999999999);
                } else {
                    return super.getInteger(attributeMetadata);
                }
            }
        };
        
        final PodamFactory factory = new PodamFactoryImpl();
        factory.getStrategy().addOrReplaceTypeManufacturer(int.class, manufacturer);
        return factory;
    }
}
