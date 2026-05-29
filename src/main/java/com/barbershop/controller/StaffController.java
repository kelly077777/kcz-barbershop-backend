package com.barbershop.controller;

import com.barbershop.model.Staff;
import com.barbershop.model.StaffStatus;
import com.barbershop.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {

    private final StaffRepository staffRepository;

    @GetMapping
    public List<Staff> getAll() {
        return staffRepository.findAll();
    }

    @GetMapping("/active")
    public List<Staff> getActive() {
        return staffRepository.findByStatus(StaffStatus.ACTIVE);
    }

    @PostMapping
    public ResponseEntity<Staff> create(@RequestBody Staff staff) {
        if (staff.getStatus() == null) staff.setStatus(StaffStatus.ACTIVE);
        return ResponseEntity.ok(staffRepository.save(staff));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> update(@PathVariable Long id, @RequestBody Staff updated) {
        return staffRepository.findById(id).map(staff -> {
            staff.setFullName(updated.getFullName());
            staff.setPhone(updated.getPhone());
            staff.setEmail(updated.getEmail());
            staff.setRole(updated.getRole());
            staff.setCommissionPercent(updated.getCommissionPercent());
            staff.setDailySalary(updated.getDailySalary());
            staff.setJoinDate(updated.getJoinDate());
            staff.setStatus(updated.getStatus());
            staff.setNotes(updated.getNotes());
            return ResponseEntity.ok(staffRepository.save(staff));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        staffRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}