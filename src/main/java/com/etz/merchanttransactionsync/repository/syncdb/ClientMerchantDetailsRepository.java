/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.repository.syncdb;

import com.etz.merchanttransactionsync.model.syncdb.ClientMerchantDetails;
import com.etz.merchanttransactionsync.model.syncdb.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 *
 * @author afam.okonkwo
 */
@Slf4j
@Repository
@Component
public class ClientMerchantDetailsRepository //extends JpaRepository<ClientMerchantDetails, String>
{

    @Autowired
    @Qualifier("syncDbEntityManagerFactory")
    private EntityManager entityManager;

    public ClientMerchantDetails  findClientMerchantDetails(String clientId) {
        ClientMerchantDetails clientMerchantDetails = (ClientMerchantDetails) entityManager.find(ClientMerchantDetails.class, clientId);
        return  clientMerchantDetails;

    }
}

