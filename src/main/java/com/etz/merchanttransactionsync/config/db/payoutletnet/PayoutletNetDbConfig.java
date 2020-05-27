package com.etz.merchanttransactionsync.config.db.payoutletnet;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

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
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSourceProperties payoutletDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "payoutletDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource dataSource() {
        log.info("The RETRIEVED payoutletDb DataSource Url is ::{}", payoutletDataSourceProperties().getUrl());
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(payoutletDataSourceProperties().getDriverClassName());
        dataSource.setUrl(payoutletDataSourceProperties().getUrl());
        dataSource.setUsername(payoutletDataSourceProperties().getUsername());
        dataSource.setPassword(payoutletDataSourceProperties().getPassword());
        return dataSource;
        //return DataSourceBuilder.create().build();

        //return payoutletDataSourceProperties().initializeDataSourceBuilder()
          //      .type(HikariDataSource.class).build();
    }


    @Bean(name = "payoutletDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
            entityManagerFactory(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("payoutletDataSource") DataSource dataSource
            ) {
        return builder
                .dataSource(dataSource)
                .packages("com.etz.merchanttransactionsync.model.payoutletnet")
                .persistenceUnit("webdb")
                .build();
    }

    @Bean(name = "payoutletDbtransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("payoutletDbEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }


}
