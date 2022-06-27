package com.airxelerate.inventory.service.impl;

import com.airxelerate.inventory.dto.FlightDTO;
import com.airxelerate.inventory.exception.FlightNotFoundException;
import com.airxelerate.inventory.model.Flight;
import com.airxelerate.inventory.repository.FlightRepository;
import com.airxelerate.inventory.service.FlightService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, ModelMapper modelMapper) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public FlightDTO add(FlightDTO flightDTO) {
        logger.info("adding new flight: " + flightDTO);
        return convertToFlightDTO(flightRepository.save(convertToFlight(flightDTO)));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public FlightDTO getById(int id) {
        logger.info("retrieving flight by ID: " + id);
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isPresent()) {
            return convertToFlightDTO(flight.get());
        } else {
            throw new FlightNotFoundException(id);
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<FlightDTO> getAll() {
        logger.info("retrieving all flights");
        return flightRepository.findAll().stream().map(this::convertToFlightDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public FlightDTO deleteById(int id) {
        logger.info("deleting flight by ID: " + id);
        Optional<Flight> flightToDelete = flightRepository.findById(id);
        if (flightToDelete.isPresent()) {
            flightRepository.deleteById(id);
            return convertToFlightDTO(flightToDelete.get());
        } else {
            throw new FlightNotFoundException(id);
        }
    }

    private Flight convertToFlight(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, Flight.class);
    }

    private FlightDTO convertToFlightDTO(Flight flight) {
        return modelMapper.map(flight, FlightDTO.class);
    }
}
