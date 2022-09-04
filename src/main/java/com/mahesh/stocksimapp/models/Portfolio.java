package com.mahesh.stocksimapp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "portfolio")
public class Portfolio implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String tradeId;
    private String stockSymbol;
    private Integer quantity;
    private Double pricePerShare;
    private Double totalPrice;

    // @ManyToOne
    // private User user;

    @Temporal(TemporalType.DATE)
    private Date dateOfTrade;

    public Portfolio(String tradeId, String stockSymbol, Integer quantity, Double pricePerShare, Double totalPrice) {
        this.tradeId = tradeId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalPrice = totalPrice;
    }
}
