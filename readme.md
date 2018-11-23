# ICA Banken

Unofficial Java API client for ICA Banken.

## Authentication
- Username and password

## Features
- "Mina Sidor" - account overview
- List card accounts

## Code examples
Get all accounts balance.
```java
// Login
IcaBankenApi icaBankenApi = new IcaBankenApi(socialSecurityNumber, password);

// Mina sidor / account overview
MinaSidorResponse minaSidor = icaBankenApi.getMinaSidor();

// Accounts balance
for (Account account : minaSidor.getAccounts()) {
    System.out.println(account.getName() + " = " + account.getAvailableAmount());
}
```

Get all card names.
```java
// Login
IcaBankenApi icaBankenApi = new IcaBankenApi(socialSecurityNumber, password);

// All cards
for (Card card : icaBankenApi.getCardAccounts().getCards()) {
    System.out.println(card.getCardTypeDescription());
}
```

## Thanks
Heavily based on the work made by https://github.com/svendahlstrand/ica-api.