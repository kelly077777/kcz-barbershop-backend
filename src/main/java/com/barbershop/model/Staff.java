package com.barbershop.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String phone;

    private String email;

    private String role;

    private Double commissionPercent = 0.0;

    private Double dailySalary = 0.0;

    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    private StaffStatus status = StaffStatus.ACTIVE;

    private String notes;
}