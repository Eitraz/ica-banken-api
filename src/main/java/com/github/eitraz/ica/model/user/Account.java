package com.github.eitraz.ica.model.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public class Account {
    @JsonAlias("Name")
    private String name;

    @JsonAlias("AccountNumber")
    private String accountNumber;

    @JsonAlias("AvailableAmount")
    private Double availableAmount;

    @JsonAlias("ReservedAmount")
    private Double reservedAmount;

    @JsonAlias("Saldo")
    private Double saldo;

    @JsonAlias("CreditLimit")
    private Double creditLimit;
}
