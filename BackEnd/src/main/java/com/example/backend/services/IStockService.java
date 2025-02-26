package com.example.backend.services;

import com.example.backend.entities.Stock;

import java.util.List;

public interface IStockService {
    Stock addStock(Stock stock);
    List<Stock> getAllStocks();
    Stock getStockById(Integer id);
    Stock updateStock(Integer id, Stock stock);
    boolean deleteStock(Integer id);
}
