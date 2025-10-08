package com.proyecto.customer.application.service;

import com.proyecto.customer.application.port.in.*;
import com.proyecto.customer.application.port.out.CustomerRepositoryPort;
import com.proyecto.customer.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CustomerService implements CreateCustomerUseCase, FindCustomerUseCase {

    private final CustomerRepositoryPort repository;
    private static final Map<String, String> VALID_DOCUMENTS = Map.of(
            "PERSONAL", "DNI",
            "VIP", "DNI",
            "BUSINESS", "RUC",
            "PYME", "RUC"
    );


    @Override
    public Mono<Customer> create(Customer customer) {
        // Validación 1: tipo de cliente y tipo de documento coherentes
        String expectedDoc = VALID_DOCUMENTS.get(customer.getCustomerType().toUpperCase());

        if (expectedDoc == null) {
            return Mono.error(new IllegalArgumentException("Unknown customer type: " + customer.getCustomerType()));
        }

        if (!expectedDoc.equalsIgnoreCase(customer.getDocumentType())) {
            return Mono.error(new IllegalArgumentException(
                    String.format("Customer type %s must have document type %s",
                            customer.getCustomerType(), expectedDoc)));
        }

        // Validación 2: documento duplicado
        return repository.findByDocumentTypeAndDocumentNumber(customer.getDocumentType(), customer.getDocumentNumber())
                .flatMap(existing ->
                        Mono.<Customer>error(new IllegalArgumentException("Customer with document already exists"))
                )
                // Validación 3: teléfono duplicado
                .switchIfEmpty(
                        repository.findByNumberPhone(customer.getNumberPhone())
                                .flatMap(existing ->
                                        Mono.<Customer>error(new IllegalArgumentException("Customer with phone number already exists"))
                                )
                )
                // Creación final
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
    public Mono<Customer> findByDocumentNumber(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber);
    }

    @Override
    public Mono<Customer> findByNumberPhone(String numberPhone) {
        return repository.findByNumberPhone(numberPhone);
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

}
