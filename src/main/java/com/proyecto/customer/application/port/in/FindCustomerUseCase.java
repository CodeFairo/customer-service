package com.proyecto.customer.application.port.in;

import com.proyecto.customer.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindCustomerUseCase {
    Mono<Customer> findById(String id);
    Flux<Customer> findAll();
}