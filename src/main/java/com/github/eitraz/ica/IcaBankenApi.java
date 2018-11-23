package com.github.eitraz.ica;

import com.github.eitraz.ica.client.Authentication;
import com.github.eitraz.ica.client.IcaBankenClient;
import com.github.eitraz.ica.model.user.CardAccounts;
import com.github.eitraz.ica.model.user.MinaSidorResponse;

@SuppressWarnings("WeakerAccess")
public class IcaBankenApi {
    private final IcaBankenClient client;

    public IcaBankenApi(String username, String password) {
        this.client = new IcaBankenClient(new Authentication(username, password));
    }

    public MinaSidorResponse getMinaSidor() {
        return client.get("user/minasidor", MinaSidorResponse.class);
    }

    public CardAccounts getCardAccounts() {
        return client.get("user/cardaccounts", CardAccounts.class);
    }
}
