package com.mahesh.stocksimapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateRequest {
    private String portfolioId;
    private String date;
}
