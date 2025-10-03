package com.proyecto.customer.application.service;

import com.proyecto.customer.application.port.in.*;
import com.proyecto.customer.application.port.out.CustomerRepositoryPort;
import com.proyecto.customer.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class CustomerService implements CreateCustomerUseCase, FindCustomerUseCase {

    private final CustomerRepositoryPort repository;

    @Override
    public Mono<Customer> create(Customer customer) {
        return repository.findByDocumentTypeAndDocumentNumber(customer.getDocumentType(), customer.getDocumentNumber())
                .flatMap(existing -> Mono.<Customer>error(new IllegalArgumentException("Customer with document already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    customer.setCreatedAt(LocalDateTime.now());
                    return repository.save(customer);
                }));
    }

    @Override
    public Mono<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

}
