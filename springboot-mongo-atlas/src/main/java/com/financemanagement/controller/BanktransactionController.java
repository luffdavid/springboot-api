package com.financemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.financemanagement.model.Banktransaction;
import com.financemanagement.service.BankaccountService;
import com.financemanagement.service.BanktransactionService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/banktransactions")
public class BanktransactionController {

    @Autowired
    private BanktransactionService service;

    @Autowired
    private BankaccountService bankaccService;

    @PostMapping
    public ResponseEntity<Banktransaction> createBanktransaction(@RequestBody Banktransaction banktransaction) {
        try {
            String accId = banktransaction.getBankaccountId();
            Bankaccount bankaccount = bankaccService.getBankaccountById(accId);

            if (bankaccount == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Account not found
            }

            if (banktransaction.getTransactionsType().equals("Income")) {
                bankaccount.setBankAccCurrentAmount(
                        bankaccount.getBankAccCurrentAmount() + banktransaction.getTransactionsAmount());
            } else if (banktransaction.getTransactionsType().equals("Expense")) {
                bankaccount.setBankAccCurrentAmount(
                        bankaccount.getBankAccCurrentAmount() - banktransaction.getTransactionsAmount());
            }

            bankaccService.updateBankaccount(bankaccount, accId);

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

    @GetMapping("/filterByName")
    public ResponseEntity<List<Banktransaction>> getBanktransactionsByName(@RequestParam("name") String name) {
        List<Banktransaction> banktrans = service.findByTransactionsName(name);
        try {
            return ResponseEntity.ok(banktrans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/filterByType")
    public ResponseEntity<List<Banktransaction>> getBankaccountsByType(@RequestParam("type") String type) {
        List<Banktransaction> banktrans = service.findByTransactionsType(type);
        try {
            return ResponseEntity.ok(banktrans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // http://localhost:8080/banktransactions/filterByBankaccountId?bankaccountId=cc273385
    @GetMapping("/filterByBankaccountId")
    public ResponseEntity<List<Banktransaction>> getBankaccountsByBankaccountId(
            @RequestParam("bankaccountId") String bankaccountId) {
        List<Banktransaction> banktrans = service.findByBankaccountId(bankaccountId);
        try {
            return ResponseEntity.ok(banktrans);
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
