package com.financemanagement.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.financemanagement.model.Bankaccount;

public interface BankaccountRepository extends MongoRepository<Bankaccount, String> {
    List<Bankaccount> findByBankAccName(String bankAccName);

    List<Bankaccount> findByBankAccType(String bankaccType);

    List<Bankaccount> findByBankAccCurrentAmount(double bankaccAmount);

    List<Bankaccount> findByUserId(String userId);
}
