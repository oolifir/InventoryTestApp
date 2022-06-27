package com.airxelerate.inventory.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Flight {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please enter IATA Carrier Code")
    @Size(min = 2, max = 2, message = "IATA Carrier Code's length should be 2 letters")
    @Column(name = "carrier_code")
    private String carrierCode;

    @NotEmpty(message = "Please enter flight number")
    @Size(max = 4, message = "flight number shouldn't be longer than 4 digits")
    @Column(name = "flight_number")
    private String flightNumber;

    @NotNull(message = "Please enter flight date")
    @Column(name = "flight_date")
    @Temporal(TemporalType.DATE)
    private Date flightDate;

    @NotEmpty(message = "Please enter origin IATA Airport Code")
    @Size(min = 3, max = 3, message = "origin IATA Airport Code's length should be 3 letters")
    @Column(name = "origin_airport_code")
    private String originAirportCode;

    @NotEmpty(message = "Please enter destination IATA Airport Code")
    @Size(min = 3, max = 3, message = "destination IATA Airport Code's length should be 3 letters")
    @Column(name = "destination_airport_code")
    private String destinationAirportCode;
}