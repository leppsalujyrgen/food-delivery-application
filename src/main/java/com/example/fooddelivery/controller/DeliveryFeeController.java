package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.DeliveryFeeResponse;
import com.example.fooddelivery.service.DeliveryFeeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryFeeController {

    private final DeliveryFeeCalculationService deliveryFeeCalculationService;

    @Autowired
    public DeliveryFeeController(DeliveryFeeCalculationService deliveryFeeCalculationService) {
        this.deliveryFeeCalculationService = deliveryFeeCalculationService;
    }

    @GetMapping("/api/delivery-fee")
    public ResponseEntity<DeliveryFeeResponse> calculateDeliveryFee(
            @RequestParam("city") String city,
            @RequestParam("vehicleType") String vehicleType
    ) {
        try {
            double deliveryFee = deliveryFeeCalculationService.calculateDeliveryFee(city, vehicleType);
            DeliveryFeeResponse response = new DeliveryFeeResponse(deliveryFee);
            return ResponseEntity.ok(response);
            
        
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DeliveryFeeResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DeliveryFeeResponse("Internal server error occurred"));
        }
    }
}