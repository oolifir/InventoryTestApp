package com.airxelerate.inventory.controller;

import com.airxelerate.inventory.dto.FlightDTO;
import com.airxelerate.inventory.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public FlightDTO addFlight(@RequestBody @Valid FlightDTO flightDTO) {
        return flightService.add(flightDTO);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public FlightDTO getById(@PathVariable("id") final int id) {
        return flightService.getById(id);
    }

    @GetMapping()
    @ResponseBody
    public List<FlightDTO> getAll() {
        return flightService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public FlightDTO delete(@PathVariable("id") final int id) {
        return flightService.deleteById(id);
    }

}
