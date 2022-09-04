package com.mahesh.stocksimapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mahesh.stocksimapp.dto.EvaluateResponse;
import com.mahesh.stocksimapp.dto.PortfolioCsvRequest;
import com.mahesh.stocksimapp.exceptions.ResourceNotFoundException;
import com.mahesh.stocksimapp.models.Portfolio;
import com.mahesh.stocksimapp.models.User;
import com.mahesh.stocksimapp.repository.PortfolioRepository;
import com.mahesh.stocksimapp.services.UserService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_PORTFOLIO;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_PORTFOLIO_BY_ID;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_PORTFOLIO_UPLOAD_CSV;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_PORTFOLIO_EVALUATE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = ENDPOINT_V1_PORTFOLIO)
public class PortfolioController {

    @Autowired
    private UserService userService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @PostMapping()
    public ResponseEntity<Portfolio> addPortfolio(@RequestBody PortfolioCsvRequest portfolioUpdates) {
        if (portfolioUpdates.getUserId() == null) throw new ResourceNotFoundException("User not found " + portfolioUpdates.getUserId());
        User user = userService.findById(portfolioUpdates.getUserId());

        Double totalPricePerShare = portfolioUpdates.getPricePerShare() * portfolioUpdates.getQuantity();

        if ((user.getBuyingPower() - totalPricePerShare) < 0) {
            throw new ResourceNotFoundException("User does not have enough buying power.");
        }
        
        Portfolio portfolio = new Portfolio();
        // portfolio.setUserId(portfolioUpdates.getUserId());
        portfolio.setStockSymbol(portfolioUpdates.getStockSymbol());
        portfolio.setQuantity(portfolioUpdates.getQuantity());
        portfolio.setPricePerShare(portfolioUpdates.getPricePerShare());
        portfolio.setTotalPrice(totalPricePerShare);
        portfolio.setDateOfTrade(new Date());
        final Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
        return ResponseEntity.ok(updatedPortfolio);
    }

    @PostMapping(path = ENDPOINT_V1_PORTFOLIO_UPLOAD_CSV, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<User>> addPortfolioWithCsv(@RequestParam("file") MultipartFile file) {
    // public ResponseEntity<List<Portfolio>> addPortfolioWithCsv(@RequestParam("file") MultipartFile file) {
        
        // validate file
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("Please Upload a CSV File!");
        } else {

            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<PortfolioCsvRequest> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(PortfolioCsvRequest.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<PortfolioCsvRequest> portfolioRequests = csvToBean.parse();
                
                // List<Portfolio> portfolios = new ArrayList<>();
                List<User> users = new ArrayList<>();

                for (PortfolioCsvRequest portfolioRequest: portfolioRequests) {
                    Portfolio portfolio = new Portfolio();
                    // user.setUserId(portfolioRequest.getUserId());
                    portfolio.setStockSymbol(portfolioRequest.getStockSymbol());
                    portfolio.setQuantity(portfolioRequest.getQuantity());
                    portfolio.setPricePerShare(portfolioRequest.getPricePerShare());
                    portfolio.setDateOfTrade(portfolioRequest.getDateOfTrade());
                    portfolio.setTotalPrice(portfolioRequest.getPricePerShare() * portfolioRequest.getQuantity());
                    // portfolios.add(portfolio);

                    boolean portfolioExists = false;
                    for (User user: users) {
                        if (user.getUserId().equals(portfolioRequest.getUserId())) {
                            List<Portfolio> userPortfolios = user.getPortfolios();
                            userPortfolios.add(portfolio);
                            user.setPortfolios(userPortfolios);
                            portfolioExists = true;
                            break;
                        }
                    }

                    if (!portfolioExists) {
                        User newuser = new User();
                        newuser.setUserId(portfolioRequest.getUserId());
                        List<Portfolio> userPortfolios = new ArrayList<Portfolio>();
                        userPortfolios.add(portfolio);
                        newuser.setPortfolios(userPortfolios);
                        users.add(newuser);
                    }

                }
                return ResponseEntity.ok(userService.addUsers(users));
                // return ResponseEntity.ok(portfolioRepository.saveAll(portfolios));
            } catch (Exception ex) {
                throw new ResourceNotFoundException(ex.toString());
            }
        }

    }

    @GetMapping()
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        return ResponseEntity.ok(portfolioRepository.findAll());
    }

    @GetMapping(path = ENDPOINT_V1_PORTFOLIO_BY_ID)
    public ResponseEntity<Portfolio> findTradeById(@PathVariable(value = "id") String tradeId) {
        Portfolio portfolio = portfolioRepository.findById(tradeId).orElseThrow(
            () -> new ResourceNotFoundException("Portfolio not found " + tradeId)
        );
        return ResponseEntity.ok().body(portfolio);
    }
    @PutMapping(path = ENDPOINT_V1_PORTFOLIO_BY_ID)
    public ResponseEntity<Portfolio> updateBudgetOfUser(@PathVariable(value = "id") String tradeId,
                                                   @RequestBody PortfolioCsvRequest portfolioUpdates) {
        Portfolio portfolio = portfolioRepository.findById(tradeId).orElseThrow(
            () -> new ResourceNotFoundException("Portfolio not found " + tradeId)
        );
        portfolio.setStockSymbol(portfolioUpdates.getStockSymbol());
        portfolio.setQuantity(portfolioUpdates.getQuantity());
        portfolio.setPricePerShare(portfolioUpdates.getPricePerShare());
        portfolio.setTotalPrice(portfolioUpdates.getPricePerShare() * portfolioUpdates.getQuantity());

        final Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
        return ResponseEntity.ok(updatedPortfolio);

    }

    @DeleteMapping(path = ENDPOINT_V1_PORTFOLIO_BY_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") String tradeId) {
        Portfolio portfolio = portfolioRepository.findById(tradeId).orElseThrow(
            () -> new ResourceNotFoundException("Portfolio not found " + tradeId)
        );
        portfolioRepository.delete(portfolio);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = ENDPOINT_V1_PORTFOLIO_EVALUATE)
    public ResponseEntity<EvaluateResponse> getEvaluation(@RequestParam String userId, @RequestParam String evaluationDate) throws ParseException {
        User user = userService.findById(userId);
        Double initialBudget = user.getBuyingPower(); 

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(evaluationDate);

        Double sumOfTotalPrice = portfolioRepository.findTotalSpendNativeQuery(userId, date).getEvaluatedBudget();
        Double evaluatedBuyingPower = null;
        if (sumOfTotalPrice == null) {
            evaluatedBuyingPower = initialBudget;
        } else {
            evaluatedBuyingPower = initialBudget - sumOfTotalPrice;
        }

        EvaluateResponse evaluateResponse = new EvaluateResponse(evaluatedBuyingPower);
        evaluateResponse.setEvaluatedBudget(evaluatedBuyingPower);

        return ResponseEntity.ok(evaluateResponse);

    }
    
}
