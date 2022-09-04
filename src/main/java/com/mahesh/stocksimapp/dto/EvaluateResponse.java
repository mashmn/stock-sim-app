package com.mahesh.stocksimapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
// @AllArgsConstructor
// @NoArgsConstructor
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EvaluateResponse {
    private Double evaluatedBudget;

    public EvaluateResponse(Double evaluatedBudget) {
        this.evaluatedBudget = evaluatedBudget;
    }

}
