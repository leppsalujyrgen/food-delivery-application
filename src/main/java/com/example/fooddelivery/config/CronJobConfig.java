package com.example.fooddelivery.config;

import com.example.fooddelivery.entity.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;
import com.example.fooddelivery.util.WeatherDataRetriever;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.xml.sax.SAXException;

@Configuration
@EnableScheduling
public class CronJobConfig {

	private final WeatherDataRepository weatherDataRepository;

	@Autowired
	public CronJobConfig(WeatherDataRepository weatherDataRepository) {
		this.weatherDataRepository = weatherDataRepository;
	}

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

	@Scheduled(cron = "0 15 * * * *") // Runs every hour at 15 minutes past the hour
	public void importWeatherDataOnSchedule() {
		try {
			List<WeatherData> weatherDataList = WeatherDataRetriever.getDefaultStationsData();
			weatherDataRepository.saveAll(weatherDataList);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}