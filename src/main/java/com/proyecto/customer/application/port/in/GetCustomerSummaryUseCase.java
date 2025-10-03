package com.proyecto.customer.application.port.in;

import com.proyecto.customer.domain.model.CustomerSummary;
import reactor.core.publisher.Mono;

public interface GetCustomerSummaryUseCase {
    Mono<CustomerSummary> getSummary(String customerId);
}