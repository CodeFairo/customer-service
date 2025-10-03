package com.proyecto.customer.application.port.in;

import com.proyecto.customer.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CreateCustomerUseCase {
    Mono<Customer> create(Customer customer);
}
