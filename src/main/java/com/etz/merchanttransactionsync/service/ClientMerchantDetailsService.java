package com.etz.merchanttransactionsync.service;

import com.etz.merchanttransactionsync.model.syncdb.ClientMerchantDetails;
import com.etz.merchanttransactionsync.repository.syncdb.ClientMerchantDetailsRepository;
import java.util.Date;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientMerchantDetailsService {

    @Autowired
    ClientMerchantDetailsRepository clientMerchantDetailsRepository;

    public void  createClientMerchantDetails() {
        /*
        ClientMerchantDetails clientMerchantDetails = new ClientMerchantDetails();
        clientMerchantDetails.setClientId("KGIRS_SERVICE");
        clientMerchantDetails.setMerchantName("KOGI STATE KGIRS");
        clientMerchantDetails.setEmails("afam.okonkwo@etranzactng.com,afamsimon@gmail.com");
        clientMerchantDetails.setLastSyncedTime(new Date());
        clientMerchantDetails.setCreatedBy(0L);
        clientMerchantDetails.setCreatedDate(new Date());
        clientMerchantDetails.setLastModifiedBy(0L);
        clientMerchantDetails.setLastModifiedDate(new Date());
        clientMerchantDetails.setAccessTokenValidity(6000);
        clientMerchantDetails.setAuthorities("KGIRS_SERVICE");
        clientMerchantDetails.setAuthorizedGrantTypes("password,refresh_token,client_credentials");
        clientMerchantDetails.setRefreshTokenValidity(12000);
        clientMerchantDetails.setResourceIds("kgirs-service");
        clientMerchantDetails.setWebServerRedirectUri("www.tiffani-oberbrunner.biz");
        clientMerchantDetails.setScope("read,write");
        clientMerchantDetails.setAutoapprove("true");

        clientMerchantDetails = clientMerchantDetailsRepository.save(clientMerchantDetails);
        log.info("Successfully Created Client:::{}", clientMerchantDetails);

         */
        
        
    }

    public ClientMerchantDetails  findClientMerchantDetails(String clientId) {
       ClientMerchantDetails clientMerchantDetails = clientMerchantDetailsRepository.findClientMerchantDetails(clientId);
        log.info("Successfully Retreived Client:::{}", clientMerchantDetails);
        return  clientMerchantDetails;

    }
}
