package com.raffaelbrandao.creditanalysis.entity.payload.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class CreditCardProposalResponse {
    private Long id;

    private Long cardholderEntityId;

    @NotNull
    private String cardholderEntityName;

    @NotNull
    private String cardholderEntityNumber;

    @NotNull
    @Email
    private String cardholderEntityEmail;

    @NotNull
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardholderEntityId() {
        return cardholderEntityId;
    }

    public void setCardholderEntityId(Long cardholderEntityId) {
        this.cardholderEntityId = cardholderEntityId;
    }

    public String getCardholderEntityName() {
        return cardholderEntityName;
    }

    public void setCardholderEntityName(String cardholderEntityName) {
        this.cardholderEntityName = cardholderEntityName;
    }

    public String getCardholderEntityNumber() {
        return cardholderEntityNumber;
    }

    public void setCardholderEntityNumber(String cardholderEntityNumber) {
        this.cardholderEntityNumber = cardholderEntityNumber;
    }

    public String getCardholderEntityEmail() {
        return cardholderEntityEmail;
    }

    public void setCardholderEntityEmail(String cardholderEntityEmail) {
        this.cardholderEntityEmail = cardholderEntityEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
