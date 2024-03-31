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

public class WeatherDataRetriever {
	
	public static String weatherDataUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
	public static Collection<String> weatherStationNames = Stream.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu").collect(Collectors.toCollection(HashSet::new));
	
	public static List<WeatherData> getWeatherStationData() throws SAXException, IOException, ParserConfigurationException {
		Document xmlWeatherData = requestWeatherData();
		List<WeatherData> weatherStationDataList = getWeatherStationData(xmlWeatherData, weatherStationNames);
        return weatherStationDataList;
    }
	
	public static List<WeatherData> getWeatherStationData(Document xmlWeatherData, Collection<String> weatherStationNames) throws SAXException, IOException, ParserConfigurationException {
		Element observations = (Element) xmlWeatherData.getElementsByTagName("observations").item(0);
	    String timestamp = observations.getAttribute("timestamp");
	    NodeList stations = xmlWeatherData.getElementsByTagName("station");
	    
	    List<WeatherData> weatherStationDataList = new ArrayList<>();
	    for (int i = 0; i < stations.getLength(); i++) {
	    	Element stationElement = (Element) stations.item(i);
            WeatherData stationWeatherData = new WeatherData();
            
            NodeList stationChildNodes = stationElement.getChildNodes();
            for (int j = 0; j < stationChildNodes.getLength(); j++) {
                Node stationEntry = stationChildNodes.item(j);
                String entryName = stationEntry.getNodeName();
                String entryValue = stationEntry.getTextContent();
                if(entryValue==null) {
            		entryValue="";
            	}
                
                if (entryName.equalsIgnoreCase("name")) {
                	stationWeatherData.setStationName(entryValue);
            	}
                else if (entryName.equalsIgnoreCase("wmocode")) {
                	stationWeatherData.setWmoCode(entryValue);
                }
                else if (entryName.equalsIgnoreCase("airtemperature")) {
                	try {
                		stationWeatherData.setAirTemperature(Double.valueOf(entryValue));
	                } catch (Exception e) {
	            		stationWeatherData.setWindSpeed(0.0);
	            	}
                }
                else if (entryName.equalsIgnoreCase("windspeed")) {
                	try {
                		stationWeatherData.setWindSpeed(Double.valueOf(entryValue));
                	} catch (Exception e) {
                		stationWeatherData.setWindSpeed(0.0);
                	}
    			}
                else if (entryName.equalsIgnoreCase("phenomenon")) {
            		stationWeatherData.setWeatherPhenomenon(entryValue);
                }
            }
            stationWeatherData.setTimestamp(timestamp);

            if (weatherStationNames.contains(stationWeatherData.getStationName())) {
                weatherStationDataList.add(stationWeatherData);
            }
        }
	    
	    
        return weatherStationDataList;
    }
	
	
	public static Document requestWeatherData() throws SAXException, IOException, ParserConfigurationException {
		// Query the URL (dummy implementation, no actual processing of XML)
        RestTemplate restTemplate = new RestTemplate();
        String xmlData = restTemplate.getForObject(weatherDataUrl, String.class);
        
        // Parse the XML data string to a Document object
		Document xmlDocument = DocumentBuilderFactory.
				newInstance().
				newDocumentBuilder().
				parse(new ByteArrayInputStream(xmlData.getBytes()));
		return xmlDocument;
	}
	
}
