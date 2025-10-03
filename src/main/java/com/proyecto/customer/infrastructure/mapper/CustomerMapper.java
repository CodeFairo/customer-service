package com.proyecto.customer.infrastructure.mapper;

import com.proyecto.customer.domain.model.Customer;
import com.proyecto.customer.domain.model.CustomerSummary;
import com.proyecto.customer.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerMapper {

    /**
     * Convierte un CustomerRequestDTO (input de API) a entidad de dominio
     */
    public Customer toDomain(CustomerRequestDTO dto) {
        if (dto == null) return null;

        return Customer.builder()
                .customerType(dto.getCustomerType().name())
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .businessName(dto.getBusinessName())
                .status("ACTIVE")                 // default en creación
                .createdAt(LocalDateTime.now())   // timestamp
                .build();
    }


    /**
     * Convierte entidad de dominio a CustomerDTO (respuesta de API)
     */
    public CustomerDTO toDto(Customer customer) {
        if (customer == null) return null;

        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setCustomerType(customer.getCustomerType());
        dto.setDocumentType(customer.getDocumentType());
        dto.setDocumentNumber(customer.getDocumentNumber());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setBusinessName(customer.getBusinessName());
        dto.setStatus(CustomerDTO.StatusEnum.valueOf(customer.getStatus())); // si el DTO también usa enum
        return dto;
    }


    /**
     * Convierte entidad de dominio a CustomerSummaryDTO (respuesta extendida)
     * ⚠️ Aquí lo dejamos básico, normalmente mapearías accounts/credits/cards desde otros agregados
     */
    public CustomerSummaryDTO toSummaryDto(Customer customer) {
        if (customer == null) return null;

        CustomerSummaryDTO summary = new CustomerSummaryDTO();
        summary.setId(customer.getId());
        summary.setFullName(customer.getFirstName() + " " + customer.getLastName());
        // cuentas, créditos y tarjetas vendrían de otros servicios
        return summary;
    }

    public CustomerSummaryDTO toSummaryDto(CustomerSummary summary) {
        if (summary == null) return null;

        CustomerSummaryDTO dto = new CustomerSummaryDTO();
        dto.setId(summary.getId());
        dto.setFullName(summary.getFullName());

        dto.setAccounts(summary.getAccounts().stream()
                .map(acc -> {
                    AccountSummaryDTO a = new AccountSummaryDTO();
                    a.setId(acc.getId());
                    a.setType(acc.getType());
                    a.setBalance(acc.getBalance());
                    return a;
                })
                .toList());

        dto.setCredits(summary.getCredits().stream()
                .map(crd -> {
                    CreditSummaryDTO c = new CreditSummaryDTO();
                    c.setId(crd.getId());
                    c.setType(crd.getType());
                    c.setOutstandingBalance(crd.getOutstandingBalance());
                    return c;
                })
                .toList());

        dto.setCards(summary.getCards().stream()
                .map(card -> {
                    CardSummaryDTO cd = new CardSummaryDTO();
                    cd.setId(card.getId());
                    cd.setType(card.getType());
                    cd.setAvailableCredit(card.getAvailableCredit());
                    return cd;
                })
                .toList());

        return dto;
    }

}
