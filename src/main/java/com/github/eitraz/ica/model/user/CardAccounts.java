package com.github.eitraz.ica.model.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public class CardAccounts {
    @JsonAlias("CustomerNumber")
    private long customerNumber;

    @JsonAlias("Cards")
    private List<Card> cards;
}
