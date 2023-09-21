package com.financemanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.financemanagement.model.Banktransaction;

public interface BanktransactionRepository extends MongoRepository<Banktransaction, String> {
    // List<Bankaccount> findByBankAccName(String bankAccName);

    // List<Bankaccount> findByBankAccType(String bankaccType);

    // List<Bankaccount> findByBankAccCurrentAmount(double bankaccAmount);
}
