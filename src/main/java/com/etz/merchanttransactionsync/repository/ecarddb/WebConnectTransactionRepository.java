package com.etz.merchanttransactionsync.repository.ecarddb;

import com.etz.merchanttransactionsync.model.ecarddb.WebConnectTransactionLog;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface WebConnectTransactionRepository extends JpaRepository<WebConnectTransactionLog,String> {

    @Query("SELECT wctl FROM WebConnectTransactionLog wctl WHERE wctl.terminalId=:Terminal_Id AND wctl.response = '0' AND wctl.transDate BETWEEN :lastSyncedTime AND CURRENT_TIMESTAMP")
    List<WebConnectTransactionLog> queryAllMerchantSpecificSuccessfullTransactionsNotSyncedYet
            (@Param("Terminal_Id")String terminalId, @Param("lastSyncedTime") Date lastSyncedTime);
}
