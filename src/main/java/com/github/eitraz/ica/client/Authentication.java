package com.github.eitraz.ica.client;

import com.github.eitraz.ica.model.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Authentication {
    private static final Logger logger = LoggerFactory.getLogger(Authentication.class);

    private final String username;
    private final String password;

    private LocalDateTime expires = LocalDateTime.now().minusDays(1);
    private String authenticationTicket;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void login(IcaBankenClient client) {
        if (authenticationTicket == null || LocalDateTime.now().isAfter(expires)) {
            logger.info("Session not available or expired, logging in");

            // Login header
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Basic " + base64Encode(username + ":" + password));

            // Login
            HttpResponse<LoginResponse> response = client.get("login", headers, LoginResponse.class);
            LoginResponse loginResponse = response.getBody();

            // Get TTL and authentication ticket
            expires = LocalDateTime.now().plusSeconds(loginResponse.getTtl()).minusMinutes(1);
            authenticationTicket = response.getHeaders().get("AuthenticationTicket");

            logger.info("Logged in, session expires at " + expires);
        }
    }

    void invalidateSession() {
        authenticationTicket = null;
    }

    Map<String, String> getHeaders(IcaBankenClient client) {
        login(client);
        return Collections.singletonMap("AuthenticationTicket", authenticationTicket);
    }

    private static String base64Encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes(StandardCharsets.UTF_8));
    }
}
