package com.example.backend.services;

import com.example.backend.entities.Stock;
import com.example.backend.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private EmailService emailService;

    @Override
    public Stock addStock(Stock stock) {
        validateStock(stock);
        Stock savedStock = stockRepository.save(stock);
        
        // Envoyer l'email de notification
        try {
            emailService.sendStockNotification(
                savedStock.getName(),
                "destinataire@email.com" // Email du destinataire
            );
        } catch (Exception e) {
            // Log l'erreur mais ne pas bloquer la création du stock
            System.err.println("Erreur lors de l'envoi de l'email: " + e.getMessage());
        }
        
        return savedStock;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockById(Integer id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public Stock updateStock(Integer id, Stock stock) {
        Stock existingStock = stockRepository.findById(id).orElse(null);
        if (existingStock != null) {
            validateStock(stock);
            
            existingStock.setName(stock.getName());
            existingStock.setDescription(stock.getDescription());
            existingStock.setQuantity(stock.getQuantity());
            existingStock.setUnitPrice(stock.getUnitPrice());
            existingStock.setCategory(stock.getCategory());
            return stockRepository.save(existingStock);
        }
        return null;
    }

    @Override
    public boolean deleteStock(Integer id_stock) {
        if (stockRepository.existsById(id_stock)) {
            stockRepository.deleteById(id_stock);
            return true;
        }
        return false;
    }

    private void validateStock(Stock stock) {
        if (stock.getQuantity() > 10000) {
            throw new IllegalArgumentException("La quantité ne peut pas dépasser 10000 unités");
        }
        if (stock.getUnitPrice() > 100000) {
            throw new IllegalArgumentException("Le prix unitaire ne peut pas dépasser 100000");
        }
    }
} 