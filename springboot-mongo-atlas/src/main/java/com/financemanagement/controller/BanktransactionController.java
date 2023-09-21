package com.financemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financemanagement.model.Banktransaction;
import com.financemanagement.service.BanktransactionService;

@RestController
@RequestMapping("/banktransactions")
public class BanktransactionController {

    @Autowired
    private BanktransactionService service;

    @PostMapping
    public ResponseEntity<Banktransaction> createBanktransaction(@RequestBody Banktransaction banktransaction) {
        try {
            Banktransaction createdBankTransaction = service.createBanktransaction(banktransaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBankTransaction);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Banktransaction>> getBanktransactions() {
        try {
            List<Banktransaction> banktransactions = service.getBanktransactions();
            if (banktransactions.isEmpty()) {
                return ResponseEntity.noContent().build(); // Keine Bankkonten vorhanden
            }
            return ResponseEntity.ok(banktransactions); // Erfolgreiche Antwort mit Bankkontenliste
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{banktransId}")
    public ResponseEntity<Banktransaction> getBanktransaction(@PathVariable String banktransId) {
        try {
            return ResponseEntity.ok(service.getBanktransactionById(banktransId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{banktransId}")
    public ResponseEntity<Banktransaction> updateBanktransaction(@RequestBody Banktransaction banktransaction,
            @PathVariable String banktransId) {
        try {
            Banktransaction updatedBanktrans = service.updateBanktransaction(banktransaction, banktransId);
            if (updatedBanktrans != null) {
                return ResponseEntity.ok(updatedBanktrans);
            } else {
                return ResponseEntity.notFound().build(); // Bankkonto nicht gefunden
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{banktransId}")
    public ResponseEntity<String> deleteBanktransaction(@PathVariable String banktransId) {
        try {
            String deletedBanktrans = service.deleteBanktransaction(banktransId);
            if (deletedBanktrans != null) {
                return ResponseEntity.ok(deletedBanktrans);
            } else {
                return ResponseEntity.notFound().build(); // Bankkonto nicht gefunden
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
