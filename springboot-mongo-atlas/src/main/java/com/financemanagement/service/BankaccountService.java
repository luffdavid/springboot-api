package com.financemanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financemanagement.model.Bankaccount;
import com.financemanagement.repository.BankaccountRepository;

@Service
public class BankaccountService {

    @Autowired
    private BankaccountRepository bankaccountRepository;

    // CREATE a new Bankaccount
    public Bankaccount createBankaccount(Bankaccount bankaccount) {
        bankaccount.setBankAccId(UUID.randomUUID().toString().split("-")[0]);
        return bankaccountRepository.save(bankaccount);
    }

    // Get all bankaccountd
    public List<Bankaccount> getBankaccounts() {
        return bankaccountRepository.findAll();
    }

    // GET one bankacc by ID
    public Bankaccount getBankaccountById(String bankaccId) {
        return bankaccountRepository.findById(bankaccId).get();
    }

    // Filter by Name
    public List<Bankaccount> findByBankaccountName(String bankaccName) {
        return bankaccountRepository.findByBankAccName(bankaccName);
    }

    // Filter by Amount
    public List<Bankaccount> findByBankAccountCurrentAmount(double bankaccAmount) {
        return bankaccountRepository.findByBankAccCurrentAmount(bankaccAmount);
    }

    // Filter by Type
    public List<Bankaccount> findByBankAccType(String bankaccType) {
        return bankaccountRepository.findByBankAccType(bankaccType);
    }

    // UPDATE Bankacc
    public Bankaccount updateBankaccount(Bankaccount bankaccount, String bankaccId) {
        // get the existing document from DB
        // populate new value from request to existing object/entity/document
        Bankaccount existingBankacc = bankaccountRepository.findById(bankaccount.getBankAccId()).get();
        existingBankacc.setBankAccName(bankaccount.getBankAccName());
        existingBankacc.setBankAccType(bankaccount.getBankAccType());
        existingBankacc.setBankAccCurrentAmount(bankaccount.getBankAccCurrentAmount());

        return bankaccountRepository.save(existingBankacc);
    }

    // DELETE bankacc
    public String deleteBankaccount(String bankaccId) {
        bankaccountRepository.deleteById(bankaccId);
        return bankaccId + " Bankaccount deleted";
    }

}
