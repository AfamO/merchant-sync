package com.etz.merchanttransactionsync.repository.ecarddb;

import com.etz.checkmarxfix.jparesult.ResultUtil;
import com.etz.merchanttransactionsync.domain.request.PaginationRequest;
import com.etz.merchanttransactionsync.model.ecarddb.WebConnectTransactionLog;
import org.hibernate.criterion.CriteriaQuery;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class WebConnectTransactionRepository<T>  {

    @Autowired
    @Qualifier("ecardDbEntityManagerFactory")
    private EntityManager entityManager;

    public <T> Optional<T> findOneOptional(Class<T> type, Object id) {
        T obj = entityManager.find(type, id);
        if (!StringUtils.isEmpty(obj)) {
            entityManager.detach(obj);
            return Optional.of(obj);
        }
        return Optional.empty();
    }

    public <T, K> List<T> findAllById(Class<T> classz, Set<K> ids) {
        TypedQuery<T> typeQuery = entityManager.createQuery("SELECT k FROM " + classz.getSimpleName() + " k WHERE k.id in :Ids",
                classz);
        typeQuery.setParameter("Ids", ids);
        return ResultUtil.fetchList(typeQuery);
    }



    public List<T> queryAllMerchantSpecificSuccessfullTransactionsNotSyncedYet(String terminalId, Date lastSyncedTime) {
        //String query = "SELECT wctl FROM WebConnectTransactionLog wctl WHERE wctl.terminalId=:Terminal_Id AND wctl.response = '0' AND wctl.transDate BETWEEN :lastSyncedTime AND CURRENT_TIMESTAMP";
        //TypedQuery<T> entityManager.createQuery()
        return null;
    }


    /*
    @Query("SELECT wctl FROM WebConnectTransactionLog wctl WHERE wctl.terminalId=:Terminal_Id AND wctl.response = '0' AND wctl.transDate BETWEEN :lastSyncedTime AND CURRENT_TIMESTAMP")
    List<WebConnectTransactionLog> queryAllMerchantSpecificSuccessfullTransactionsNotSyncedYet
            (@Param("Terminal_Id")String terminalId, @Param("lastSyncedTime") Date lastSyncedTime);

     */
}
