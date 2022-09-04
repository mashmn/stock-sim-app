package com.mahesh.stocksimapp.dto;

import java.io.Serializable;
import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioCsvRequest implements Serializable{
    @CsvBindByName(column = "userid")
    private String userId;

    @CsvBindByName(column = "stocksymbol")
    private String stockSymbol;

    @CsvBindByName(column = "quantity")
    @CsvNumber("#")
    private Integer quantity;

    @CsvBindByName(column = "pricepershare")
    @CsvNumber("#.##")
    private Double pricePerShare;

    @CsvBindByName(column = "dateoftrade")
    @CsvDate("yyyy-MM-dd")
    private Date dateOfTrade;
}
