package com.proyecto.customer.infrastructure.adapter.out.customer;

import com.proyecto.customer.application.port.out.CustomerCreatedEventPublisherPort;
import com.proyecto.customer.domain.model.Customer;
import com.proyecto.customer.avro.CustomerCreatedEvent;
import com.proyecto.wallet.avro.WalletCreationRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomerCreatedEventPublisherAdapter implements CustomerCreatedEventPublisherPort {

    private static final String TOPIC = "customer.created";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Mono<Void> publish(Customer customer, WalletCreationRequestedEvent originEvent) {
        var event = CustomerCreatedEvent.newBuilder()
                .setCustomerId(customer.getId())
                .setWalletId(originEvent.getWalletId())
                .setDocumentType(customer.getDocumentType())
                .setDocumentNumber(customer.getDocumentNumber())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setCreatedAt(customer.getCreatedAt().toString())
                .build();

        return Mono.fromFuture(kafkaTemplate.send(TOPIC, customer.getDocumentNumber(), event).toCompletableFuture())
                .doOnSuccess(r -> System.out.printf("CustomerCreatedEvent published for %s%n", customer.getDocumentNumber()))
                .then();
    }
}
