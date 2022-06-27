package com.airxelerate.inventory.service;

import com.airxelerate.inventory.dto.FlightDTO;

import java.util.List;

public interface FlightService {

    FlightDTO add(FlightDTO flightDTO);

    FlightDTO getById(int id);

    List<FlightDTO> getAll();

    FlightDTO deleteById(int id);

}
