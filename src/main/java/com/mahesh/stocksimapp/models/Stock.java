package com.mahesh.stocksimapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String stockId;
    private String name;
    private Date date;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Integer volume;
}
