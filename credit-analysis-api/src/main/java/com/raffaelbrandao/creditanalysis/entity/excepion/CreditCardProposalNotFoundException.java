package com.raffaelbrandao.creditanalysis.entity.excepion;

public class CreditCardProposalNotFoundException extends RuntimeException {
    private String message;

    public CreditCardProposalNotFoundException(String message) {
        super(message);
    }
}
