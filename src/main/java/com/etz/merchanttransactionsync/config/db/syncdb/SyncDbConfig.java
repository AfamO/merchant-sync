/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.config.db.syncdb;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author afam.okonkwo
 */
@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "syncDbEntityManagerFactory",
        basePackages = {"com.etz.merchanttransactionsync.repository.syncdb"}
)
@Slf4j
public class SyncDbConfig {
    @Value("${spring.third-datasource.url}")
    private String url;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.third-datasource")
    public DataSourceProperties syncDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "syncDbDataSource")
    @ConfigurationProperties(prefix = "spring.third-datasource")
    public DataSource dataSource() {
        log.info("The RETRIEVED syncDb-first- DataSource Url is ::{}", url);
        log.info("The RETRIEVED syncDb DataSource Url is ::{}", syncDataSourceProperties().getUrl());
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(syncDataSourceProperties().getDriverClassName());
        dataSource.setUrl(syncDataSourceProperties().getUrl());
        dataSource.setUsername(syncDataSourceProperties().getUsername());
        dataSource.setPassword(syncDataSourceProperties().getPassword());
        return dataSource;
        //return DataSourceBuilder.create().build();
       // return syncDataSourceProperties().initializeDataSourceBuilder()
         //       .build();
    }

    @Primary
    @Bean(name = "syncDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
            syncDbEntityManagerFactory(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("syncDbDataSource") DataSource dataSource
            ) {
        return builder
                .dataSource(dataSource)
                .packages("com.etz.merchanttransactionsync.model.syncdb")
                .persistenceUnit("trans_sync_db")
                .build();
    }

    @Primary
    @Bean(name = "syncDbTransactionManager")
    public PlatformTransactionManager ecardDbTransactionManager(
            @Qualifier("syncDbEntityManagerFactory") EntityManagerFactory ecardDbEntityManagerFactory
    ) {
        return new JpaTransactionManager(ecardDbEntityManagerFactory);
    }
}

