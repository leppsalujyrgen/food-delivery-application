package com.example.fooddelivery.dto;

/**
 * Represents the response for delivery fee calculation.
 */
public class DeliveryFeeResponse {

    /** The calculated delivery fee. */
    private double deliveryFee;

    /** Error message in case of calculation failure. */
    private String errorMessage;

    /**
     * Constructs a DeliveryFeeResponse object with the given delivery fee.
     * 
     * @param deliveryFee The calculated delivery fee.
     */
    public DeliveryFeeResponse(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    /**
     * Constructs a DeliveryFeeResponse object with the given error message.
     * 
     * @param errorMessage Error message indicating the reason for calculation failure.
     */
    public DeliveryFeeResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Retrieves the calculated delivery fee.
     * 
     * @return The delivery fee.
     */
    public double getDeliveryFee() {
        return deliveryFee;
    }

    /**
     * Sets the delivery fee.
     * 
     * @param deliveryFee The delivery fee to set.
     */
    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    /**
     * Retrieves the error message.
     * 
     * @return The error message indicating the reason for calculation failure.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     * 
     * @param errorMessage The error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}