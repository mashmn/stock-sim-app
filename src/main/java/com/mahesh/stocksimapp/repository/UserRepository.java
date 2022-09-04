package com.mahesh.stocksimapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahesh.stocksimapp.models.User;

public interface UserRepository extends JpaRepository<User, String>{
    
}
