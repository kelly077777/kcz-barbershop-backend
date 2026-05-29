package com.barbershop.controller;

import com.barbershop.model.Appointment;
import com.barbershop.model.AppointmentStatus;
import com.barbershop.repository.AppointmentRepository;
import com.barbershop.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/today")
    public List<Appointment> getToday() {
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return appointmentRepository.findByAppointmentTimeBetweenOrderByAppointmentTimeAsc(start, end);
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        if (appointment.getService() != null && appointment.getService().getId() != null) {
            serviceRepository.findById(appointment.getService().getId())
                    .ifPresent(appointment::setService);
        }
        appointment.setStatus(AppointmentStatus.PENDING);
        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setStatus(AppointmentStatus.valueOf(body.get("status")));
            return ResponseEntity.ok(appointmentRepository.save(appointment));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody Appointment updated) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setClientName(updated.getClientName());
            appointment.setClientPhone(updated.getClientPhone());
            appointment.setBarberName(updated.getBarberName());
            appointment.setAppointmentTime(updated.getAppointmentTime());
            appointment.setNotes(updated.getNotes());
            if (updated.getService() != null && updated.getService().getId() != null) {
                serviceRepository.findById(updated.getService().getId())
                        .ifPresent(appointment::setService);
            }
            return ResponseEntity.ok(appointmentRepository.save(appointment));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}