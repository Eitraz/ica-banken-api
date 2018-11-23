package com.github.eitraz.ica.model.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public class Card {
    @JsonAlias("Accounts")
    private List<CardAccount> accounts;

    @JsonAlias("CardTypeDescription")
    private String cardTypeDescription;

    @JsonAlias("CardTypeCode")
    private String cardTypeCode;

    @JsonAlias("MaskedCardNumber")
    private String maskedCardNumber;

    @JsonAlias("Selected")
    private boolean selected;
}
