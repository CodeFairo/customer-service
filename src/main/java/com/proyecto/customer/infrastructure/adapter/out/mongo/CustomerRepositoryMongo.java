package com.proyecto.customer.infrastructure.adapter.out.mongo;

import com.proyecto.customer.application.port.out.CustomerRepositoryPort;
import com.proyecto.customer.domain.model.Customer;
import com.proyecto.customer.infrastructure.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryMongo implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository repository;
    private final CustomerMapper customerMapper;
    @Override
    public Mono<Customer> save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Mono<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Customer> findByDocumentTypeAndDocumentNumber(String docType, String docNumber) {
        return repository.findByDocumentTypeAndDocumentNumber(docType, docNumber);
    }
}
