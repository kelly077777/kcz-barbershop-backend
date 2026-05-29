package com.barbershop.controller;

import com.barbershop.model.Service;
import com.barbershop.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    @GetMapping
    public List<Service> getAll() {
        return serviceRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Service> create(@RequestBody Service service) {
        service.setActive(true);
        return ResponseEntity.ok(serviceRepository.save(service));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> update(@PathVariable Long id, @RequestBody Service updated) {
        return serviceRepository.findById(id).map(service -> {
            service.setName(updated.getName());
            service.setDescription(updated.getDescription());
            service.setPrice(updated.getPrice());
            service.setDurationMinutes(updated.getDurationMinutes());
            service.setActive(updated.getActive());
            return ResponseEntity.ok(serviceRepository.save(service));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        serviceRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}