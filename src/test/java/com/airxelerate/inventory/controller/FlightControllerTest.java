package com.airxelerate.inventory.controller;

import com.airxelerate.inventory.dto.FlightDTO;
import com.airxelerate.inventory.exception.FlightNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlightControllerTest {

    private static final String FLIGHT_CONTROLLER_PATH = "/flight";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    public void findAll() throws Exception
    {
        MvcResult mvcResult = mockMvc.perform(get(FLIGHT_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<FlightDTO> flightDTOResponseList = objectMapper.setDateFormat(SIMPLE_DATE_FORMAT).
                readValue(contentAsString, new TypeReference<>() {
                });

        assertEquals(getFlightDTOExpectedList(), flightDTOResponseList);
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    public void findById() throws Exception
    {
        String endpointPath = "/{id}";
        FlightDTO flightDTOExpected = getFlightDTOExpected();

        MvcResult mvcResult = mockMvc.perform(get(FLIGHT_CONTROLLER_PATH + endpointPath, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        FlightDTO flightDTOResponse = objectMapper.setDateFormat(SIMPLE_DATE_FORMAT).readValue(contentAsString, FlightDTO.class);

        assertEquals(flightDTOExpected, flightDTOResponse);
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    public void findById_NotFoundException() throws Exception {
        String endpointPath = "/{id}";

        try {
            mockMvc.perform(get(FLIGHT_CONTROLLER_PATH + endpointPath, 6)
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (NestedServletException e) {
            Exception exception =
                    assertThrows(
                            FlightNotFoundException.class,
                            () -> {
                                throw e.getCause();
                            });
            assertEquals(exception.getMessage(), "Flight with id: 6 not found");
        }
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    public void addFlight_OK() throws Exception
    {
        FlightDTO flightDTOForCreate = getFlightDTOExpected();

        MvcResult mvcResult = mockMvc.perform(post(FLIGHT_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightDTOForCreate)))
                        .andExpect(status().isCreated()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        FlightDTO flightDTOResponse = objectMapper.setDateFormat(SIMPLE_DATE_FORMAT).readValue(contentAsString, FlightDTO.class);

        assertEquals(flightDTOForCreate, flightDTOResponse);
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    public void addFlight_ForbiddenForUserRole() throws Exception
    {
        FlightDTO flightDTOForCreate = getFlightDTOExpected();

        mockMvc.perform(post(FLIGHT_CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flightDTOForCreate)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    public void deleteFlight_OK() throws Exception {
        String endpointPath = "/{id}";

        mockMvc.perform(delete(FLIGHT_CONTROLLER_PATH + endpointPath, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    public void deleteFlight_NotFoundException() throws Exception {
        String endpointPath = "/{id}";

        mockMvc.perform(delete(FLIGHT_CONTROLLER_PATH + endpointPath, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        try {
            mockMvc.perform(delete(FLIGHT_CONTROLLER_PATH + endpointPath, 1)
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (NestedServletException e) {
            Exception exception =
                    assertThrows(
                            FlightNotFoundException.class,
                            () -> {
                                throw e.getCause();
                            });
            assertEquals(exception.getMessage(), "Flight with id: 1 not found");
        }
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    public void deleteFlight_ForbiddenForUserRole() throws Exception {
        String endpointPath = "/{id}";

        mockMvc.perform(delete(FLIGHT_CONTROLLER_PATH + endpointPath, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private FlightDTO getFlightDTOExpected() throws ParseException {
        SIMPLE_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC-01"));
        Date date = SIMPLE_DATE_FORMAT.parse ("2022-06-27");

        return new FlightDTO("AC", "1010", date, "USA", "ABI");
    }

    private List<FlightDTO> getFlightDTOExpectedList() throws ParseException {
        List<FlightDTO> expectedFlightsDTOList = new ArrayList<>();

        expectedFlightsDTOList.add(new FlightDTO("AC", "1010", SIMPLE_DATE_FORMAT.parse ("2022-06-27"), "USA", "ABI"));
        expectedFlightsDTOList.add(new FlightDTO("AF", "4234", SIMPLE_DATE_FORMAT.parse ("2022-06-27"), "ADA", "LEI"));
        expectedFlightsDTOList.add(new FlightDTO("AR", "028", SIMPLE_DATE_FORMAT.parse ("2022-06-28"), "AGA", "NCY"));
        expectedFlightsDTOList.add(new FlightDTO("EI", "417", SIMPLE_DATE_FORMAT.parse ("2022-06-29"), "SUM", "ESB"));
        expectedFlightsDTOList.add(new FlightDTO("LH", "2008", SIMPLE_DATE_FORMAT.parse ("2022-07-01"), "AYU", "TNR"));

        return expectedFlightsDTOList;
    }
}