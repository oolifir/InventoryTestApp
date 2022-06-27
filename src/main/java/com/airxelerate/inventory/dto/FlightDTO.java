package com.airxelerate.inventory.dto;

import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FlightDTO {

    @Pattern(regexp = "[A-Z]{2}", message = "carrier code should be in uppercase and with length 2")
    private String carrierCode;

    @Pattern(regexp = "\\d{4}", message = "flight number should contain only digits and have length 4")
    private String flightNumber;

    @NotNull(message = "Please enter flight date")
    @Temporal(TemporalType.DATE)
    private Date flightDate;

    @Pattern(regexp = "[A-Z]{3}", message = "origin airport code should be in uppercase and with length 3")
    private String originAirportCode;

    @Pattern(regexp = "[A-Z]{3}", message = "destination airport code should be in uppercase and with length 3")
    private String destinationAirportCode;
}
