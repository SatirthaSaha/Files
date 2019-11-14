package com.travelport.acs.redis.connector;

import org.junit.Ignore;

//TODO Commented as part of NTAC Model upgrade and unavailability of SeatingChart Object.
@Ignore
public class SeatingChartMockCacheConnectorTest {
	
	/*@Test
	public void testSeatingChartMockCacheConnector_PrePopulatedValues(){
		final SeatingChartMockCacheConnector connector = new SeatingChartMockCacheConnector("redisCacheSeatingChart.mock");
		SeatingChart seatChart= new SeatingChart();
		Identifier identifier = new Identifier();
		identifier.setValue("A0656EFF-FAF4-456F-B061-0161008D7C4G");
		identifier.setAuthority("Travelport");
		seatChart.setIdentifier(identifier);
		connector.save("AK330789", seatChart);
		assertEquals("Values Should Be Same.", connector.getValue("AK330789").getIdentifier().getValue(), "A0656EFF-FAF4-456F-B061-0161008D7C4G");
		assertEquals("Values Should Be Same.", connector.getValue("AK3201234").getIdentifier().getValue(), "uITtjPMHpy");
		assertEquals("Values Should Be Same.", connector.getValue("AK3201234").getRow().get(0).getSection(), "mcyd97uwRC");
		assertEquals("Values Should Be Same.", connector.getValue("AK3201234").getRow().get(0).getSpace().get(0).getLocation(), "NAq6hMhBBR");
		
	}*/
}