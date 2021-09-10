package com.raffaelbrandao.creditanalysis.service;

import com.raffaelbrandao.creditanalysis.entity.payload.request.CreditCardProposalRequest;
import com.raffaelbrandao.creditanalysis.entity.payload.response.CreditCardProposalResponse;

public interface ProposalService {
    CreditCardProposalResponse create(CreditCardProposalRequest creditCardProposalRequest);

    CreditCardProposalResponse update(CreditCardProposalRequest creditCardProposalRequest);

    void delete(Long id);

    CreditCardProposalResponse get(Long id);
}
