package com.example.fooddelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.fooddelivery.entity.WeatherData;
import com.example.fooddelivery.util.FeeCalculations;
import com.example.fooddelivery.util.WeatherDataRetriever;

@SpringBootTest
class FoodDeliveryApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Test if weather data is requested successfully from external weather API.")
	public void testMethod_requestWeatherData() {
		try {
			Document xmlData = WeatherDataRetriever.requestWeatherData();
			assertTrue(xmlData.getChildNodes().getLength() > 0, "Expected weather request response to contain entries");
			NodeList stations = xmlData.getElementsByTagName("station");
			assertTrue(stations.getLength() > 10,
					"Expected weather request response to contain at least 10 weather stations");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			fail("Error on request: \n" + e.toString());
		}
	}

	@Test
	@DisplayName("Test if weather data from default stations is retrieved successfully.")
	public void testMethod_getDefaultStationsData() {
		try {
			// Call the method under test
			Collection<String> stationNames = WeatherDataRetriever.defaultStationNames;
			List<WeatherData> stationDataList = WeatherDataRetriever.getDefaultStationsData();
			assertEquals(stationNames.size(), stationDataList.size(),
					"Expected " + stationNames.size() + " weather data entries");
		} catch (SAXException e) {
			fail("SAX Exception: \n" + e.toString());
		} catch (IOException e) {
			fail("IO Exception: \n" + e.toString());
		} catch (ParserConfigurationException e) {
			fail("Parser error: \n" + e.toString());
		}
	}

	@Test
	@DisplayName("Test if delivery fees are calculated correctly.")
	public void testMethod_calculateRegionalBaseFee() {
		WeatherData weatherData = new WeatherData("Tartu-Tõravere", null, -2.1, 4.7, "Light snow shower", null);
		double rbf = FeeCalculations.calculateRegionalBaseFee("Tartu", "Bike");
		double extraFee = FeeCalculations.calculateExtraFee(weatherData, "Bike");
		assertEquals(4.0, rbf+extraFee, 0.000001);
	}

}
