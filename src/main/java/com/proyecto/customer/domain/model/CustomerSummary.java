package com.proyecto.customer.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerSummary {
    private String id;
    private String fullName;
    private List<AccountInfo> accounts;
    private List<CreditInfo> credits;
    private List<CardInfo> cards;

    @Data @Builder
    public static class AccountInfo {
        private String id;
        private String type;
        private Double balance;
    }

    @Data @Builder
    public static class CreditInfo {
        private String id;
        private String type;
        private Double outstandingBalance;
    }

    @Data @Builder
    public static class CardInfo {
        private String id;
        private String type;
        private Double availableCredit;
    }
}
