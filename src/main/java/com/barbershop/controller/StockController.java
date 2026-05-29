package com.barbershop.controller;

import com.barbershop.model.Stock;
import com.barbershop.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StockController {

    private final StockRepository stockRepository;

    @GetMapping
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @GetMapping("/low")
    public List<Stock> getLowStock() {
        return stockRepository.findByQuantityLessThanEqual(5);
    }

    @PostMapping
    public ResponseEntity<Stock> create(@RequestBody Stock stock) {
        stock.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(stockRepository.save(stock));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody Stock updated) {
        return stockRepository.findById(id).map(stock -> {
            stock.setProductName(updated.getProductName());
            stock.setCategory(updated.getCategory());
            stock.setQuantity(updated.getQuantity());
            stock.setLowStockThreshold(updated.getLowStockThreshold());
            stock.setUnitPrice(updated.getUnitPrice());
            stock.setUnit(updated.getUnit());
            stock.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(stockRepository.save(stock));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/restock")
    public ResponseEntity<Stock> restock(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        return stockRepository.findById(id).map(stock -> {
            stock.setQuantity(stock.getQuantity() + body.get("quantity"));
            stock.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(stockRepository.save(stock));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        stockRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}