package com.proyecto.customer.domain.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String documentNumber) {
        super("Customer with document [" + documentNumber + "] already exists");
    }
}
