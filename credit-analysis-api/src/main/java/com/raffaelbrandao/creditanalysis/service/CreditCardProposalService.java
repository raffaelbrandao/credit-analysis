package com.raffaelbrandao.creditanalysis.service;

import com.raffaelbrandao.creditanalysis.entity.CreditCardProposalEntity;
import com.raffaelbrandao.creditanalysis.entity.CreditCardProposalStatusEnum;
import com.raffaelbrandao.creditanalysis.entity.excepion.CreditCardProposalNotFoundException;
import com.raffaelbrandao.creditanalysis.entity.payload.request.CreditCardProposalRequest;
import com.raffaelbrandao.creditanalysis.entity.payload.response.CreditCardProposalResponse;
import com.raffaelbrandao.creditanalysis.repository.CreditCardProposalRepository;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardProposalService implements ProposalService {
    private final CreditCardProposalRepository creditCardProposalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CreditCardProposalService(CreditCardProposalRepository creditCardProposalRepository, ModelMapper modelMapper) {
        this.creditCardProposalRepository = creditCardProposalRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public CreditCardProposalResponse create(CreditCardProposalRequest creditCardProposalRequest)
            throws ConfigurationException, MappingException, IllegalArgumentException {
        CreditCardProposalEntity creditCardProposalEntity = modelMapper.map(creditCardProposalRequest, CreditCardProposalEntity.class);
        creditCardProposalEntity.setStatus(CreditCardProposalStatusEnum.PENDING);
        creditCardProposalEntity = creditCardProposalRepository.save(creditCardProposalEntity);

        return modelMapper.map(creditCardProposalEntity, CreditCardProposalResponse.class);
    }

    @Transactional
    @Override
    public CreditCardProposalResponse update(CreditCardProposalRequest creditCardProposalRequest)
            throws ConfigurationException, MappingException, IllegalArgumentException {
        CreditCardProposalEntity creditCardProposalEntity = modelMapper.map(creditCardProposalRequest, CreditCardProposalEntity.class);
        creditCardProposalEntity.setStatus(CreditCardProposalStatusEnum.PENDING);
        creditCardProposalEntity = creditCardProposalRepository.save(creditCardProposalEntity);

        return modelMapper.map(creditCardProposalEntity, CreditCardProposalResponse.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        creditCardProposalRepository.deleteById(id);
    }

    public CreditCardProposalResponse get(Long id)
            throws ConfigurationException, MappingException, IllegalArgumentException, CreditCardProposalNotFoundException {
        Optional<CreditCardProposalEntity> creditCardProposal = creditCardProposalRepository.findById(id);
        if (!creditCardProposal.isPresent()) {
            throw new CreditCardProposalNotFoundException(String.format("Not found credit card proposal with %s", id));
        }

        return modelMapper.map(creditCardProposal.get(), CreditCardProposalResponse.class);
    }

    public List<CreditCardProposalResponse> getAll()
            throws ConfigurationException, MappingException, IllegalArgumentException, CreditCardProposalNotFoundException {
        List<CreditCardProposalResponse> CreditCardProposalResponseList = new ArrayList<>();

        creditCardProposalRepository.findAll().stream().forEach(p -> CreditCardProposalResponseList.add(modelMapper.map(p, CreditCardProposalResponse.class)));

        return CreditCardProposalResponseList;
    }

    public CreditCardProposalResponse updateStatus(Long id, CreditCardProposalStatusEnum creditCardProposalStatusEnum)
            throws ConfigurationException, MappingException, IllegalArgumentException {
        Optional<CreditCardProposalEntity> creditCardProposal = creditCardProposalRepository.findById(id);
        CreditCardProposalEntity creditCardProposalEntity = creditCardProposal.get();
        creditCardProposalEntity.setStatus(creditCardProposalStatusEnum);
        creditCardProposalEntity = creditCardProposalRepository.save(creditCardProposalEntity);

        return modelMapper.map(creditCardProposal.get(), CreditCardProposalResponse.class);
    }
}
