package com.etz.merchanttransactionsync.repository.payoutletnet;

import com.etz.merchanttransactionsync.model.payoutletnet.PayOutletTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayOutletTransactionRepository extends JpaRepository<PayOutletTransactionLog,Long> {

    @Query("SELECT ptl from PayOutletTransactionLog ptl where ptl.transMerchantCode=:merchantCode")
    List<PayOutletTransactionLog> findByUniqueMerchantCode(@Param("merchantCode") String merchantCode);
    
    @Override
    Optional<PayOutletTransactionLog> findById(Long id);
}

/*

 */