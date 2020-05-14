package com.etz.merchanttransactionsync.service;

import com.etz.merchanttransactionsync.model.ecarddb.WebConnectTransactionLog;
import com.etz.merchanttransactionsync.repository.ecarddb.WebConnectTransactionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class WebConnectService {

    @Autowired
    WebConnectTransactionRepository webConnectTransactionRepository;

    public List<WebConnectTransactionLog> getUnsyncedTransactions(String terminalId, Date lastSyncedTime) {
        return  webConnectTransactionRepository.queryAllMerchantSpecificSuccessfullTransactionsNotSyncedYet(terminalId, lastSyncedTime);
    }
}
