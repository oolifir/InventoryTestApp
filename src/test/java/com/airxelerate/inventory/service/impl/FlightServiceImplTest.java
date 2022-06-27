package com.airxelerate.inventory.service.impl;

import com.airxelerate.inventory.dto.FlightDTO;
import com.airxelerate.inventory.exception.FlightNotFoundException;
import com.airxelerate.inventory.model.Flight;
import com.airxelerate.inventory.repository.FlightRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FlightServiceImplTest {

    @Mock
    FlightRepository flightRepository;
    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    FlightServiceImpl flightService;

    @Test
    public void getById_OK() {
        Flight flight = new Flight(1, "AC", "1010", new Date(), "USA", "ABI");

        when(flightRepository.findById(flight.getId())).thenReturn(Optional.of(flight));

        FlightDTO flightDTOExpected = modelMapper.map(flight, FlightDTO.class);
        FlightDTO flightDTOActual = flightService.getById(flight.getId());

        assertEquals(flightDTOExpected, flightDTOActual);
    }

    @Test(expected = FlightNotFoundException.class)
    public void getById_FlightNotFoundExceptionWhenFlightIsNotFound() {
        flightService.getById(2);
    }

    @Test
    public void findAll_OK() {
        List<Flight> flights = getAllFlights();

        when(flightRepository.findAll()).thenReturn(flights);

        List<FlightDTO> flightDTOExpectedList = flights.stream().map(element -> modelMapper.map(element, FlightDTO.class)).collect(Collectors.toList());
        List<FlightDTO> flightDTOActualList = flightService.getAll();

        assertEquals(flightDTOExpectedList, flightDTOActualList);
    }

    @Test(expected = FlightNotFoundException.class)
    public void delete_FlightNotFoundExceptionWhenFlightIsNotFound() {
        flightService.deleteById(1);
    }

    private List<Flight> getAllFlights() {
        ArrayList<Flight> flightList = new ArrayList<>();
        flightList.add(new Flight(1, "AC", "1011", new Date(), "USA", "ABI"));
        flightList.add(new Flight(2, "AC", "1012", new Date(), "USA", "ABI"));
        flightList.add(new Flight(3, "AC", "1013", new Date(), "USA", "ABI"));

        return flightList;
    }

}