package com.mahesh.stocksimapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahesh.stocksimapp.dto.StockRequest;
import com.mahesh.stocksimapp.models.Stock;
import com.mahesh.stocksimapp.repository.StockRepository;

import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_STOCK;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = ENDPOINT_V1_STOCK)
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    // @PostMapping()
    // public Stock addStockData(@RequestBody Stock stock) {
    //     return stockRepository.save(stock);
    // }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<List<Stock>> addStockDataList(@RequestBody List<StockRequest> stockRequestLists) throws ParseException {

        List<Stock> stocks = new ArrayList<>();  
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        for (StockRequest stockRequest: stockRequestLists) {
            Stock stock = new Stock();
            Date date = null;

            stock.setClose(stockRequest.getClose());
            date = formatter.parse(stockRequest.getDate());
            stock.setDate(date);
            stock.setHigh(stockRequest.getHigh());
            stock.setLow(stockRequest.getLow());
            stock.setName(stockRequest.getName());
            stock.setOpen(stockRequest.getOpen());
            stock.setVolume(stockRequest.getVolume());
            stocks.add(stock);
        }
        return ResponseEntity.ok(stockRepository.saveAll(stocks));
    }

    @GetMapping()
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockRepository.findAll());
    }

    // @GetMapping(path = ENDPOINT_V1_STOCK_BY_ID)
    // public ResponseEntity<Stock> getStock(@PathVariable(value = "id") String name) {
    //     Stock stock = stockRepository.findById(name).orElseThrow(
    //         () -> new ResourceNotFoundException("Stock not found " + name)
    //     );
    //     return ResponseEntity.ok().body(stock);
    // }
    
}
