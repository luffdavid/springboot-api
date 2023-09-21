package com.financemanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "bankaccounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bankaccount {
    @Id
    private String bankAccId;
    private String bankAccName;
    private double bankAccCurrentAmount;
    private String bankAccType;
}
