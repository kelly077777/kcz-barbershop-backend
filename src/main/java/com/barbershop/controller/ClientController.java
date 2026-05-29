package com.barbershop.controller;

import com.barbershop.model.Client;
import com.barbershop.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/search")
    public List<Client> search(@RequestParam String name) {
        return clientRepository.findByFullNameContainingIgnoreCase(name);
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
        client.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(clientRepository.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client updated) {
        return clientRepository.findById(id).map(client -> {
            client.setFullName(updated.getFullName());
            client.setPhone(updated.getPhone());
            client.setEmail(updated.getEmail());
            client.setPreferredBarber(updated.getPreferredBarber());
            client.setNotes(updated.getNotes());
            return ResponseEntity.ok(clientRepository.save(client));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}