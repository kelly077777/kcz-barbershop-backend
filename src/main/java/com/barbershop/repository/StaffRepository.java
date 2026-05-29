package com.barbershop.repository;

import com.barbershop.model.Staff;
import com.barbershop.model.StaffStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByStatus(StaffStatus status);
}