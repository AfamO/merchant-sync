package com.etz.merchanttransactionsync.config.db;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author afam.okonkwo
 */
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "payoutletDbEntityManagerFactory",
        basePackages = {"com.etz.merchanttransactionsync.repository.payoutletnet"}
)
@Slf4j
public class PayoutletNetDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.fourth-datasource")
    public DataSourceProperties payoutletDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "payoutletDataSource")
    @ConfigurationProperties(prefix = "spring.fourth-datasource")
    public DataSource dataSource() {
        //com.sybase.jdbc4.jdbc.SybDriver;
        log.info("The RETRIEVED payoutletDb DataSource Url is ::{}", payoutletDataSourceProperties().getUrl());
        try {
            Connection connection = DriverManager.getConnection("jdbc:sybase:Tds:172.17.10.101:5002?ServiceName=PAYOUTLETDB","sa",null);
            log.info("Sybase Connection MetaData is ::{}", connection.getMetaData());
        } catch (SQLException e) {
            log.info("Sybase Connection Error is ::{}", e.getLocalizedMessage());
            e.printStackTrace();
        }

        return payoutletDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }


    @Bean(name = "payoutletDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
            entityManagerFactory(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("payoutletDataSource") DataSource dataSource
            ) {
                log.info("The RETRIEVED payoutletDb DataSource Url1 is ::{}", payoutletDataSourceProperties().getUrl());
        return builder
                .dataSource(dataSource)
                .packages("com.etz.merchanttransactionsync.model.payoutletnet")
                .persistenceUnit("webdb")
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("payoutletDbEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        log.info("The RETRIEVED payoutletDb DataSource Url2 is ::{}", payoutletDataSourceProperties().getUrl());
        return new JpaTransactionManager(entityManagerFactory);
    }


}
