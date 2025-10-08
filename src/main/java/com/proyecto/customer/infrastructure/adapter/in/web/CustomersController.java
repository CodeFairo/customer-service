package com.proyecto.customer.infrastructure.adapter.in.web;

import com.proyecto.customer.api.CustomersApi;
import com.proyecto.customer.application.port.in.CreateCustomerUseCase;
import com.proyecto.customer.application.port.in.FindCustomerUseCase;
import com.proyecto.customer.infrastructure.mapper.CustomerMapper;
import com.proyecto.customer.model.CustomerDTO;
import com.proyecto.customer.model.CustomerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomersController implements CustomersApi {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindCustomerUseCase findCustomerUseCase;
    private final CustomerMapper customerMapper;

    @Override
    public Mono<ResponseEntity<CustomerDTO>> customersCustomerIdGet(String customerId,
                                                                    ServerWebExchange exchange) {
        return findCustomerUseCase.findById(customerId)
                .map(customerMapper::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> customersDocumentNumberDocumentNumberGet(String documentNumber,
                                                                    ServerWebExchange exchange) {
        return findCustomerUseCase.findByDocumentNumber(documentNumber)
                .map(customerMapper::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> customersNumberPhoneNumberPhoneGet(String numberPhone,
                                                                                      ServerWebExchange exchange) {
        return findCustomerUseCase.findByNumberPhone(numberPhone)
                .map(customerMapper::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @Override
    public Mono<ResponseEntity<Flux<CustomerDTO>>> customersGet(String type,
                                                                ServerWebExchange exchange) {
        Flux<CustomerDTO> customers = findCustomerUseCase.findAll()
                .map(customerMapper::toDto);
        return Mono.just(ResponseEntity.ok(customers));
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> customersPost(Mono<CustomerRequestDTO> customerRequest,
                                                           ServerWebExchange exchange) {
        return customerRequest
                .map(customerMapper::toDomain)
                .flatMap(createCustomerUseCase::create)
                .map(customerMapper::toDto)
                .map(saved -> ResponseEntity.status(201).body(saved));
    }

}
