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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financemanagement.model.Bankaccount;
import com.financemanagement.service.BankaccountService;

@RestController
@RequestMapping("/bankaccounts")
public class BankaccountController {

    @Autowired
    private BankaccountService service;

    @PostMapping
    public ResponseEntity<Bankaccount> createBankaccount(@RequestBody Bankaccount bankacc) {
        try {
            Bankaccount createdBankAccount = service.createBankaccount(bankacc);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Bankaccount>> getBankaccounts() {
        try {
            List<Bankaccount> bankaccounts = service.getBankaccounts();
            if (bankaccounts.isEmpty()) {
                return ResponseEntity.noContent().build(); // Keine Bankkonten vorhanden
            }
            return ResponseEntity.ok(bankaccounts); // Erfolgreiche Antwort mit Bankkontenliste
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // http://localhost:8080/bankaccounts/filterByName?name=VR Bank
    @GetMapping("/filterByName")
    public ResponseEntity<List<Bankaccount>> getBankaccountsByName(@RequestParam("name") String name) {
        List<Bankaccount> bankaccounts = service.findByBankaccountName(name);
        try {
            return ResponseEntity.ok(bankaccounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/filterByType")
    public ResponseEntity<List<Bankaccount>> getBankaccountsByType(@RequestParam("type") String type) {
        List<Bankaccount> bankaccounts = service.findByBankAccType(type);
        try {
            return ResponseEntity.ok(bankaccounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/filterByAmount")
    public ResponseEntity<List<Bankaccount>> filterBankaccountsByAmount(@RequestParam("amount") double amount) {
        List<Bankaccount> filteredBankaccounts = service.findByBankAccountCurrentAmount(amount);

        if (filteredBankaccounts.isEmpty()) {
            return ResponseEntity.noContent().build(); // Keine Bankkonten mit diesem Betrag gefunden
        }

        return ResponseEntity.ok(filteredBankaccounts);
    }

    @GetMapping("/{bankaccId}")
    public ResponseEntity<Bankaccount> getBankaccount(@PathVariable String bankaccId) {
        try {
            return ResponseEntity.ok(service.getBankaccountById(bankaccId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{bankaccId}")
    public ResponseEntity<Bankaccount> updateBankaccount(@RequestBody Bankaccount bankacc,
            @PathVariable String bankaccId) {
        try {
            Bankaccount updatedBankacc = service.updateBankaccount(bankacc, bankaccId);
            if (updatedBankacc != null) {
                return ResponseEntity.ok(updatedBankacc);
            } else {
                return ResponseEntity.notFound().build(); // Bankkonto nicht gefunden
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{bankaccId}")
    public ResponseEntity<String> deleteBankaccount(@PathVariable String bankaccId) {
        try {
            String deletedBankacc = service.deleteBankaccount(bankaccId);
            if (deletedBankacc != null) {
                return ResponseEntity.ok(deletedBankacc);
            } else {
                return ResponseEntity.notFound().build(); // Bankkonto nicht gefunden
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}