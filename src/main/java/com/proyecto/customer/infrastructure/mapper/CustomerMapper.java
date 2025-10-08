package com.proyecto.customer.infrastructure.mapper;

import com.proyecto.customer.domain.model.Customer;
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
                .documentType(dto.getDocumentType().name())
                .documentNumber(dto.getDocumentNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .businessName(dto.getBusinessName())
                .status("ACTIVE")                 // default en creación
                .createdAt(LocalDateTime.now())   // timestamp
                .numberPhone(dto.getNumberPhone())
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
        dto.setStatus(CustomerDTO.StatusEnum.valueOf(customer.getStatus()));
        dto.setNumberPhone(customer.getNumberPhone());
        return dto;
    }

}
