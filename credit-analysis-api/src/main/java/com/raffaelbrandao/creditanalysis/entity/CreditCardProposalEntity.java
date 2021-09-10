package com.raffaelbrandao.creditanalysis.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "credit_card_proposal")
public class CreditCardProposalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private CardholderEntity cardholderEntity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CreditCardProposalStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardholderEntity getCardholderEntity() {
        return cardholderEntity;
    }

    public void setCardholderEntity(CardholderEntity cardholderEntity) {
        this.cardholderEntity = cardholderEntity;
    }

    public CreditCardProposalStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CreditCardProposalStatusEnum status) {
        this.status = status;
    }
}
