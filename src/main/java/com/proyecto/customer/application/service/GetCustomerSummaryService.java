package com.proyecto.customer.application.service;

import com.proyecto.customer.application.port.in.GetCustomerSummaryUseCase;
import com.proyecto.customer.domain.model.CustomerSummary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetCustomerSummaryService implements GetCustomerSummaryUseCase {

    @Override
    public Mono<CustomerSummary> getSummary(String customerId) {
        // ⚠️ Lógica mock, luego conectar con account-service, credit-service y card-service
        CustomerSummary summary = CustomerSummary.builder()
                .id(customerId)
                .fullName("Carlos Ramírez")
                .accounts(List.of(
                        CustomerSummary.AccountInfo.builder()
                                .id("acc-456")
                                .type("SAVINGS")
                                .balance(1500.75)
                                .build()
                ))
                .credits(List.of(
                        CustomerSummary.CreditInfo.builder()
                                .id("crd-789")
                                .type("PERSONAL")
                                .outstandingBalance(5000.00)
                                .build()
                ))
                .cards(List.of(
                        CustomerSummary.CardInfo.builder()
                                .id("card-101")
                                .type("PERSONAL")
                                .availableCredit(2000.00)
                                .build()
                ))
                .build();

        return Mono.just(summary);
    }
}
