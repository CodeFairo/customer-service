package com.proyecto.customer.infrastructure.adapter.out.mongo;

import com.proyecto.customer.domain.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Spring Data Reactive repository used only inside the out adapter.
 */
public interface SpringDataCustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);
    Mono<Customer> findByDocumentNumber(String documentNumber);
    Mono<Customer> findByNumberPhone(String numberPhone);
}
