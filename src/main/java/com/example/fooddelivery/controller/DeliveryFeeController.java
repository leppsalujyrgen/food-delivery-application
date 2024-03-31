package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.DeliveryFeeResponse;
import com.example.fooddelivery.service.DeliveryFeeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling requests related to delivery fees.
 * This class provides an endpoint for calculating delivery fees based on city and vehicle type.
 */
@RestController
public class DeliveryFeeController {

    @Autowired
    private DeliveryFeeCalculationService deliveryFeeCalculationService;

    /**
     * Calculates the delivery fee based on the specified city and vehicle type.
     *
     * @param cityName     the name of the city for delivery
     * @param vehicleType  the type of delivery vehicle (e.g., Car, Scooter, Bike)
     * @return a ResponseEntity containing the calculated delivery fee or an error message
     */
    @GetMapping("/api/delivery-fee/{cityName}/{vehicleType}")
    public ResponseEntity<DeliveryFeeResponse> calculateDeliveryFee(
            @PathVariable("cityName") String cityName,
            @PathVariable("vehicleType") String vehicleType
    ) {
        try {
            double deliveryFee = deliveryFeeCalculationService.calculateDeliveryFee(cityName, vehicleType);
            DeliveryFeeResponse response = new DeliveryFeeResponse(deliveryFee);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DeliveryFeeResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DeliveryFeeResponse("Internal server error occurred"));
        }
    }
}