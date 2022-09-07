package com.mahesh.stocksimapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioPostDto {
    @JsonProperty("tradeId")
    private String tradeId;

    @JsonProperty("stockSymbol")
    private String stockSymbol;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("pricePerShare")
    private Double pricePerShare;

    @JsonProperty("totalPrice")
    private Double totalPrice;
}
