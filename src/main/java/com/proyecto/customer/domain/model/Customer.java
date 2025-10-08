package com.proyecto.customer.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    private String customerType; // PERSONAL | BUSINESS | VIP | PYME
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String businessName;
    private String status; // ACTIVE | INACTIVE
    private LocalDateTime createdAt;
    private String numberPhone;
}
