package com.raffaelbrandao.creditanalysis.repository;

import com.raffaelbrandao.creditanalysis.entity.CreditCardProposalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardProposalRepository extends JpaRepository<CreditCardProposalEntity, Long> {
}
