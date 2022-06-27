package com.airxelerate.inventory.exception;

public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException(int id) {
        super("Flight with id: " + id + " not found");
    }

}
