package com.mahesh.stocksimapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mahesh.stocksimapp.models.Stock;

public interface StockRepository extends JpaRepository<Stock, String>{
    
    // @Query("SELECT s.name, s.date, s. FROM Stock s WHERE s.name = ?1")
    // Optional<Stock> findStockByName(String name);
}
