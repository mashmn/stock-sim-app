package com.mahesh.stocksimapp.mappers;

import com.mahesh.stocksimapp.dto.PortfolioPostDto;
import com.mahesh.stocksimapp.dto.UserGetDto;
import com.mahesh.stocksimapp.dto.UserPortfolioPostDto;
import com.mahesh.stocksimapp.dto.UserPostDto;
import com.mahesh.stocksimapp.models.Portfolio;
import com.mahesh.stocksimapp.models.User;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

    UserGetDto userToUserGetDto(
        User user
    );
    
    User userPostDtoToUser (
        UserPostDto userPostDto
    );

    User userPortfolioPostDtoToUser (
        UserPortfolioPostDto userPortfolioPostDto
    );

    List<User> userPortfoliosPostDtoToUsers (
        List<UserPortfolioPostDto> userPortfoliosPostDto
    );

    Portfolio portfolioPostDtoToPortfolio (
        PortfolioPostDto portfolioPostDto
    );

}
