package com.example.fooddelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.fooddelivery.entity.WeatherData;
import com.example.fooddelivery.util.WeatherDataRetriever;

@SpringBootTest
class FoodDeliveryApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
    public void testGetWeatherStationDataRequest() {
		try {
			Document xmlData = WeatherDataRetriever.requestWeatherData();
			assertTrue(xmlData.getChildNodes().getLength()>0, "Expected weather request response to contain entries");
			NodeList stations = xmlData.getElementsByTagName("station");
			assertTrue(stations.getLength()>10, "Expected weather request response to contain at least 10 weather stations");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			fail("Error on request: \n" + e.toString());
		}
	}
	
	@Test
    public void testGetWeatherStationData() {	
        try {
        	// Call the method under test
    		Collection<String> stationNames = WeatherDataRetriever.weatherStationNames;
            List<WeatherData> stationDataList = WeatherDataRetriever.getWeatherStationData();
			assertEquals(stationNames.size(), stationDataList.size(), "Expected " +stationNames.size()+ " weather data entries");
		} catch (SAXException e) {
			fail("SAX Exception: \n" + e.toString());
		} catch (IOException e) {
			fail("IO Exception: \n" + e.toString());
		} catch (ParserConfigurationException e) {
			fail("Parser error: \n" + e.toString());
		}
    }

}
