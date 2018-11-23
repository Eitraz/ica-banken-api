package com.github.eitraz.ica.model.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public class MinaSidorResponse {
    @JsonAlias("YearlyTotalPurchased")
    private Double yearlyTotalPurchased;

    @JsonAlias("AcquiredDiscount")
    private Double acquiredDiscount;

    @JsonAlias("AmountSinceLastBonusCheck")
    private Double amountSinceLastBonusCheck;

    @JsonAlias("AmountLeftUntilNextBonusCheck")
    private Double amountLeftUntilNextBonusCheck;

    @JsonAlias("AcquiredBonus")
    private Double acquiredBonus;

    @JsonAlias("IcaBankUrl")
    private String icaBankUrl;

    @JsonAlias("BonusToDate")
    private String bonusToDate;

    @JsonAlias("Accounts")
    private List<Account> accounts;
}
