package com.financemanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financemanagement.model.Banktransaction;
import com.financemanagement.repository.BanktransactionRepository;

@Service
public class BanktransactionService {

    @Autowired
    private BanktransactionRepository banktransactionRepository;

    // CREATE a new Banktransaction
    public Banktransaction createBanktransaction(Banktransaction banktransaction) {
        banktransaction.setTransactionId(UUID.randomUUID().toString().split("-")[0]);
        return banktransactionRepository.save(banktransaction);
    }

    // Get all Transactions
    public List<Banktransaction> getBanktransactions() {
        return banktransactionRepository.findAll();
    }

    // GET one banktrans by ID
    public Banktransaction getBanktransactionById(String banktransaction) {
        return banktransactionRepository.findById(banktransaction).get();
    }

    // Filter by Name
    public List<Banktransaction> findByTransactionsName(String transactionName) {
        return banktransactionRepository.findByTransactionsName(transactionName);
    }

    // // Filter by Type
    public List<Banktransaction> findByTransactionsType(String transactionType) {
        return banktransactionRepository.findByTransactionsType(transactionType);
    }

    // UPDATE Banktrans
    public Banktransaction updateBanktransaction(Banktransaction banktransaction, String banktransactionId) {
        Banktransaction existingBanktrans = banktransactionRepository.findById(banktransaction.getTransactionId())
                .get();
        existingBanktrans.setTransactionsAmount(banktransaction.getTransactionsAmount());
        existingBanktrans.setTransactionsDate(banktransaction.getTransactionsDate());
        existingBanktrans.setTransactionsName(banktransaction.getTransactionsName());
        existingBanktrans.setTransactionsType(banktransaction.getTransactionsType());

        return banktransactionRepository.save(existingBanktrans);
    }

    // DELETE bankacc
    public String deleteBanktransaction(String banktransId) {
        banktransactionRepository.deleteById(banktransId);
        return banktransId + " Banktrans deleted";
    }
}
