package com.mahesh.stocksimapp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahesh.stocksimapp.dto.EvaluateResponse;
import com.mahesh.stocksimapp.models.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, String> {

    @Query(value = "SELECT new com.mahesh.stocksimapp.dto.EvaluateResponse(sum(b.totalPrice) as totalPrice) FROM User a JOIN a.portfolios b where a.userId = :userId and b.dateOfTrade <= :dateOfTrade")
    public EvaluateResponse findTotalSpendNativeQuery(@Param("userId") String userId, @Param("dateOfTrade") Date dateOfTrade);

    // @Modifying
    // @Query("delete from Portfolio p where p.userId=:userId")
    // void deletePortfolios(@Param("userId") String userId);
}
