package com.financemanagement.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "banktransactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banktransaction {
    @Id
    private String transactionId; // generated ID
    private String bankaccountId; // associated ID with bankacc
    private String transactionsName; // e.g. Restaurant or Boat trip
    private String transactionsType; // Income or Expense
    private Date transactionsDate; // Date
    private double transactionsAmount; // Amount in double
}
