package com.proyecto.customer.infrastructure.adapter.in.kafka;

import com.proyecto.customer.application.port.out.CustomerCreatedEventPublisherPort;
import com.proyecto.wallet.avro.WalletCreationRequestedEvent;
import com.proyecto.customer.application.port.in.CreateCustomerUseCase;
import com.proyecto.customer.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletCreationConsumer {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final CustomerCreatedEventPublisherPort eventPublisher;

    @KafkaListener(topics = "wallet.creation.requested", groupId = "customer-service")
    public void handleWalletCreation(WalletCreationRequestedEvent event) {
        log.info("Received WalletCreationRequestedEvent: {}", event);

        Customer customer = new Customer();
        customer.setFirstName(event.getFirstName().toString());
        customer.setLastName(event.getLastName().toString());
        customer.setDocumentType(event.getDocumentType().toString());
        customer.setDocumentNumber(event.getDocumentNumber().toString());
        customer.setNumberPhone(event.getPhoneNumber().toString());
        customer.setCustomerType("PERSONAL");
        customer.setCreatedAt(LocalDateTime.now());

        createCustomerUseCase.create(customer)
                .flatMap(created -> eventPublisher.publish(created, event))
                .doOnSuccess(v -> log.info("Customer created and event published for wallet {}", event.getWalletId()))
                .doOnError(err -> log.error("Error processing wallet creation: {}", err.getMessage()))
                .subscribe();
    }
}
