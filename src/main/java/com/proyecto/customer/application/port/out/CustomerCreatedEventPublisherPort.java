package com.proyecto.customer.application.port.out;

import com.proyecto.customer.domain.model.Customer;
import com.proyecto.wallet.avro.WalletCreationRequestedEvent;
import reactor.core.publisher.Mono;

public interface CustomerCreatedEventPublisherPort {
    Mono<Void> publish(Customer customer, WalletCreationRequestedEvent originEvent);
}
