package com.example.fooddelivery.config;

import com.example.fooddelivery.entity.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;
import com.example.fooddelivery.util.WeatherAPI;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.xml.sax.SAXException;

/**
 * Configuration class for defining scheduled tasks (cron jobs) in the application.
 * This class handles the periodic import of weather data from the external API
 * and stores it in the database.
 */
@Configuration
@EnableScheduling
public class CronJobConfig {

    private final WeatherDataRepository weatherDataRepository;

	/*
	 * @PostConstruct // Runs on application startup
	 * public void importWeatherDataOnStartup() {
	 * try {
	 * List<WeatherData> weatherDataList =
	 * WeatherDataRetriever.getWeatherStationData();
	 * weatherDataRepository.saveAll(weatherDataList);
	 * } catch (SAXException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * } catch (IOException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * } catch (ParserConfigurationException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * }
	 */

    /**
     * Constructs a new CronJobConfig instance with the specified WeatherDataRepository.
     * 
     * @param weatherDataRepository the WeatherDataRepository to be used for storing weather data
     */
    @Autowired
    public CronJobConfig(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    /**
     * Scheduled method to import weather data from the external API and store it in the database.
     * This method runs every hour at 15 minutes past the hour according to the specified cron expression.
     */
    @Scheduled(cron = "0 15 * * * *") // Runs every hour at 15 minutes past the hour
    public void importWeatherDataOnSchedule() {
        try {
            List<WeatherData> weatherDataList = WeatherAPI.getDefaultStationsData();
            weatherDataRepository.saveAll(weatherDataList);
        } catch (SAXException e) {
            // Exception handling for SAX parsing errors
            e.printStackTrace();
        } catch (IOException e) {
            // Exception handling for IO errors
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // Exception handling for parser configuration errors
            e.printStackTrace();
        }
    }
}