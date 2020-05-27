package com.etz.merchanttransactionsync.repository.ecarddb;

import com.etz.checkmarxfix.jparesult.ResultUtil;
import com.etz.merchanttransactionsync.domain.request.PaginationRequest;
import com.etz.merchanttransactionsync.model.ecarddb.WebConnectTransactionLog;
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
public class WebConnectTransactionRepository  {

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

    public <T> Page<T> findAllBy(Class<T> classz, Map<String, Object> filter, PaginationRequest page) {
        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + classz.getSimpleName() + " e where e.");

        filter.keySet().forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i + " = :" + i + " AND"));

        sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<Long> countQuery = entityManager.createQuery(sqlQuery.get().replace("select e from", "select count(e) from"),
                Long.class);
        TypedQuery<T> typeQuery = entityManager.createQuery(sqlQuery.get(), classz);

        filter.keySet().forEach(i -> {
            typeQuery.setParameter(i, filter.get(i));
            countQuery.setParameter(i, filter.get(i));
        });

        Long contentSize = countQuery.getSingleResult();
        page.setSize(page.getSize() == 0 ? contentSize.intValue() : page.getSize());
        if (contentSize.intValue() == 0) { // Is the returned result list entityManagerpty?
            page.setSize(1); // then set the size to be 1, to avoid java.lang.IllegalArgumentException: Page
            // size must not be less than one!
        }
        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(typeQuery.getResultList(), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }


    /*
    @Query("SELECT wctl FROM WebConnectTransactionLog wctl WHERE wctl.terminalId=:Terminal_Id AND wctl.response = '0' AND wctl.transDate BETWEEN :lastSyncedTime AND CURRENT_TIMESTAMP")
    List<WebConnectTransactionLog> queryAllMerchantSpecificSuccessfullTransactionsNotSyncedYet
            (@Param("Terminal_Id")String terminalId, @Param("lastSyncedTime") Date lastSyncedTime);

     */
}
