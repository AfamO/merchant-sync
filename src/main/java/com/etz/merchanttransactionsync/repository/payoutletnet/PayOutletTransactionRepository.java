package com.etz.merchanttransactionsync.repository.payoutletnet;

import com.etz.checkmarxfix.jparesult.ResultUtil;
import com.etz.merchanttransactionsync.model.payoutletnet.PayOutletTransactionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class PayOutletTransactionRepository  {


    final private EntityManager entityManager;

    public  PayOutletTransactionRepository(final @Qualifier("payoutletDbEntityManagerFactory") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> T findOne(Class<T> type, Long id) {
        T obj = entityManager.find(type, id);
        if (!StringUtils.isEmpty(obj)) {
            entityManager.detach(obj);
            return obj;
        }
        return null;
    }

    public <T, K> List<T> findAllById(Class<T> classz, Set<K> ids) {
        TypedQuery<T> typeQuery = entityManager.createQuery("SELECT k FROM " + classz.getSimpleName() + " k WHERE k.id in :Ids",
                classz);
        typeQuery.setParameter("Ids", ids);
        return ResultUtil.fetchList(typeQuery);
    }


    //@Query("SELECT ptl from PayOutletTransactionLog ptl where ptl.transMerchantCode=:merchantCode")
    //List<PayOutletTransactionLog> findByUniqueMerchantCode(@Param("merchantCode") String merchantCode);
    

}

/*

 */