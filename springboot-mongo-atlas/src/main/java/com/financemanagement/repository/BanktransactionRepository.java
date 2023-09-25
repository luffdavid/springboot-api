package com.financemanagement.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.financemanagement.model.Banktransaction;

public interface BanktransactionRepository extends MongoRepository<Banktransaction, String> {

    List<Banktransaction> findByTransactionsName(String transactionName);

    List<Banktransaction> findByTransactionsType(String transactionsType);
}
