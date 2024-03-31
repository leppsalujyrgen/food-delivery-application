package com.example.fooddelivery.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.fooddelivery.entity.WeatherData;

public class WeatherAPI {

	public static String weatherDataUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
	public static Collection<String> defaultStationNames = Stream.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu")
			.collect(Collectors.toCollection(HashSet::new));

	public static List<WeatherData> getDefaultStationsData()
			throws SAXException, IOException, ParserConfigurationException {
		Document xmlWeatherData = requestWeatherData();
		List<WeatherData> weatherStationDataList = getWeatherStationData(xmlWeatherData, defaultStationNames);
		return weatherStationDataList;
	}

	public static List<WeatherData> getWeatherStationData(Document xmlWeatherData,
			Collection<String> weatherStationNames) throws SAXException, IOException, ParserConfigurationException {
		Element observations = (Element) xmlWeatherData.getElementsByTagName("observations").item(0);
		String timestamp = observations.getAttribute("timestamp");
		NodeList stations = xmlWeatherData.getElementsByTagName("station");

		List<WeatherData> weatherStationDataList = new ArrayList<>();
		for (int i = 0; i < stations.getLength(); i++) {
			Node station = stations.item(i);
			if (station.getNodeType() == Node.ELEMENT_NODE) {
				Element stationElement = (Element) station;
				WeatherData stationWeatherData = new WeatherData();
				stationWeatherData.setTimestamp(timestamp);

				NodeList entryNodes = stationElement.getChildNodes();
				for (int j = 0; j < entryNodes.getLength(); j++) {
					Node entry = entryNodes.item(j);
					if (entry.getNodeType() == Node.ELEMENT_NODE) {
						String entryName = entry.getNodeName();
						String entryValue = entry.getTextContent();

						// Handle null entryValue
						if (entryValue == null) {
							entryValue = "";
						}

						// Set WeatherData properties based on entryName
						switch (entryName.trim().toLowerCase()) {
							case "name":
								stationWeatherData.setStationName(entryValue);
								break;
							case "wmocode":
								stationWeatherData.setWmoCode(entryValue);
								break;
							case "airtemperature":
								try {
									stationWeatherData.setAirTemperature(Double.valueOf(entryValue));
								} catch (NumberFormatException e) {
									stationWeatherData.setAirTemperature(0.0); // Default value if parsing fails
								}
								break;
							case "windspeed":
								try {
									stationWeatherData.setWindSpeed(Double.valueOf(entryValue));
								} catch (NumberFormatException e) {
									stationWeatherData.setWindSpeed(0.0); // Default value if parsing fails
								}
								break;
							case "phenomenon":
								stationWeatherData.setWeatherPhenomenon(entryValue);
								break;
						}
					}
				}

				// Add WeatherData to the list if the station name matches
				if (weatherStationNames.contains(stationWeatherData.getStationName())) {
					weatherStationDataList.add(stationWeatherData);
				}
			}
		}

		return weatherStationDataList;
	}

	public static Document requestWeatherData() throws SAXException, IOException, ParserConfigurationException {
		RestTemplate restTemplate = new RestTemplate();
		String xmlData = restTemplate.getForObject(weatherDataUrl, String.class);

		// Parse the XML data string to a Document object
		Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(xmlData.getBytes()));
		return xmlDocument;
	}

}
