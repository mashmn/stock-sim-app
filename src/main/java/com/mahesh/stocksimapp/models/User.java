package com.mahesh.stocksimapp.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stockuser")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private Double buyingPower;

    @OneToMany(targetEntity = Portfolio.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private List<Portfolio> portfolios;
}