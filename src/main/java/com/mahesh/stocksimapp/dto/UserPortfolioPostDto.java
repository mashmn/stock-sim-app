package com.mahesh.stocksimapp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPortfolioPostDto {
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

    @JsonProperty("portfolios")
    private List<PortfolioPostDto> portfolios;
}
