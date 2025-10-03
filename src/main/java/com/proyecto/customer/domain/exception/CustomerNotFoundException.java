package com.proyecto.customer.domain.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super("Customer with id [" + id + "] was not found");
    }
}
