package com.barbershop.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    private String category;

    private Integer quantity;

    private Integer lowStockThreshold = 5;

    private Double unitPrice;

    private String unit;

    private LocalDateTime updatedAt = LocalDateTime.now();
}