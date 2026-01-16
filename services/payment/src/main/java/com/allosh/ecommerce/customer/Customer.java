package com.allosh.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
        @NotNull(message = "Firstname is required")
        String firstName,
        @NotNull(message = "LastName is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "the customer email is not correctly formatted")
        String email

) {
}
