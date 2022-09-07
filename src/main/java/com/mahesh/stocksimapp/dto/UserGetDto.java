package com.mahesh.stocksimapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetDto {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("buyingPower")
    private Double buyingPower;
}
