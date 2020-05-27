package com.etz.merchanttransactionsync.repository.syncdb;

import com.etz.merchanttransactionsync.model.syncdb.SyncedTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncTransactionRepository extends JpaRepository<SyncedTransactionLog,Long> {

    //@Query("SELECT wctl FROM SyncedTransactionLog wctl WHERE wctl.terminalId=:Terminal_Id AND wctl.response = '0' AND wctl.transDate BETWEEN :lastSyncedTime AND CURRENT_TIMESTAMP")
    //List<WebConnectTransactionLog> queryAllMerchantSpecificSuccessfullTransactionsNotSyncedYet
           // (@Param("Terminal_Id") String terminalId, @Param("lastSyncedTime") Date lastSyncedTime);
}
