package com.example.backend.repositories;

import com.example.backend.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository    extends JpaRepository<Stock,Integer> {
}
