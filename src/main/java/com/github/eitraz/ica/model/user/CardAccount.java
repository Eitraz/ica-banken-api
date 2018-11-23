package com.github.eitraz.ica.model.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.eitraz.ica.json.BalanceDeserializer;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public class CardAccount {
    @JsonAlias("AccountType")
    private String accountType;

    @JsonAlias("AccountName")
    private String accountName;

    @JsonAlias("AccountStatus")
    private String accountStatus;

    @JsonAlias("Balance")
    @JsonDeserialize(using = BalanceDeserializer.class)
    private Double balance;

    @JsonAlias("Available")
    @JsonDeserialize(using = BalanceDeserializer.class)
    private Double available;
}
