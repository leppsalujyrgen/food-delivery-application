package com.example.fooddelivery.dto;

public class DeliveryFeeResponse {

    private double deliveryFee;
    private String errorMessage;

    public DeliveryFeeResponse(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public DeliveryFeeResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
