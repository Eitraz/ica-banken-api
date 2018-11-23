package com.github.eitraz.ica.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("WeakerAccess")
@Getter
@Setter
public class LoginResponse {
    @JsonAlias("FirstName")
    private String firstName;

    @JsonAlias("LastName")
    private String lastName;

    @JsonAlias("Ttl")
    private Long ttl;

    @JsonAlias("CustomerRole")
    private Long customerRole;

    @JsonAlias("Id")
    private Double id;

    @JsonAlias("ZipCode")
    private String zipCode;

    @JsonAlias("City")
    private String city;

    @JsonAlias("Gender")
    private String gender;

    @JsonAlias("YearOfBirth")
    private String yearOfBirth;

}
