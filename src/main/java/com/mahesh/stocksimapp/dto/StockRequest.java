package com.mahesh.stocksimapp.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest implements Serializable {

    private String stockId;
    private String name;
    private String date;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Integer volume;
}
