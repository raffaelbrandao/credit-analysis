package com.raffaelbrandao.creditanalysis.controller;

import com.raffaelbrandao.creditanalysis.entity.CreditCardProposalStatusEnum;
import com.raffaelbrandao.creditanalysis.entity.payload.request.CreditCardProposalRequest;
import com.raffaelbrandao.creditanalysis.entity.payload.response.CreditCardProposalResponse;
import com.raffaelbrandao.creditanalysis.service.CreditCardProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/proposals")
public class CreditCardProposalController {
    private final CreditCardProposalService creditCardProposalService;

    @Autowired
    public CreditCardProposalController(CreditCardProposalService creditCardProposalService) {
        this.creditCardProposalService = creditCardProposalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CreditCardProposalResponse create(@RequestBody @Validated CreditCardProposalRequest creditCardProposalRequest) {
        return creditCardProposalService.create(creditCardProposalRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CreditCardProposalResponse update(@RequestBody @Validated CreditCardProposalRequest creditCardProposalRequest) {
        return creditCardProposalService.update(creditCardProposalRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Validated Long id) {
        creditCardProposalService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CreditCardProposalResponse get(@PathVariable @Validated Long id) {
        return creditCardProposalService.get(id);
    }

    @GetMapping
    @ResponseBody
    public List<CreditCardProposalResponse> getAll() {
        return creditCardProposalService.getAll();
    }

    @PutMapping("/{id}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CreditCardProposalResponse updateStatus(@PathVariable @Validated Long id,
                                                   @PathVariable @Validated String status) {

        return creditCardProposalService.updateStatus(id, CreditCardProposalStatusEnum.valueOf(status.toUpperCase(Locale.getDefault())));
    }
}
