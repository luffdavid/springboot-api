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
public class Banktransactions {
    @Id
    private String transactionId;
    private String transactionsName;
    private String transactionsType;
    private Date transactionsDate;
    private double transactionsAmount;
}
