package com.proyecto.customer.application.port.out;

import com.proyecto.customer.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepositoryPort {
    Mono<Customer> save(Customer customer);
    Mono<Customer> findById(String id);
    Flux<Customer> findAll();
    Mono<Void> deleteById(String id);
    Mono<Customer> findByDocumentTypeAndDocumentNumber(String docType, String docNumber);
}
