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
@Document(collection = "customers") // nombre de la colección en Mongo
public class Customer {

    @Id
    private String id; // Mongo lo usará como _id

    private String customerType; // PERSONAL | BUSINESS
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String businessName;
    private String status; // ACTIVE | INACTIVE
    private LocalDateTime createdAt;
}
