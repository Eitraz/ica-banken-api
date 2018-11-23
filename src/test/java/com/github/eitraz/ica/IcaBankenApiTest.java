package com.github.eitraz.ica;

import com.github.eitraz.ica.model.user.CardAccounts;
import com.github.eitraz.ica.model.user.MinaSidorResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

class IcaBankenApiTest {
    private static IcaBankenApi api;

    @BeforeAll
    static void setApi() {
        api = new IcaBankenApi(
                requireNonNull(System.getProperty("username"), "System property 'username' is required (using for example -Dusername=[USERNAME])"),
                requireNonNull(System.getProperty("password"), "System property 'password' is required (using for example -Dpassword=[PASSWORD])")
        );
    }

    @Test
    void getMinaSidor() {
        MinaSidorResponse minaSidor = api.getMinaSidor();

        assertThat(minaSidor.getAcquiredBonus()).isNotNull();
        assertThat(minaSidor.getIcaBankUrl()).isEqualTo("https://mobil.icabanken.se");
        assertThat(minaSidor.getAccounts()).isNotNull();
    }

    @Test
    void getCardAccounts() {
        CardAccounts cardAccounts = api.getCardAccounts();

        assertThat(cardAccounts.getCustomerNumber()).isGreaterThan(0);
        assertThat(cardAccounts.getCards()).isNotNull();
    }
}