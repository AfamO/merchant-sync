/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.config.db.ecarddb;

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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.DriverManager;

/**
 *
 * @author afam.okonkwo
 */
@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "ecardDbEntityManagerFactory",
        basePackages = {"com.etz.merchanttransactionsync.repository.ecarddb"}
)
@Slf4j
public class EcardDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties ecardbDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ecardDbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        log.info("The RETRIEVED ecardDb DataSource Url is ::{}", ecardbDataSourceProperties().getUrl());
        //return DataSourceBuilder.create().build();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(ecardbDataSourceProperties().getDriverClassName());
        dataSource.setUrl(ecardbDataSourceProperties().getUrl());
        dataSource.setUsername(ecardbDataSourceProperties().getUsername());
        dataSource.setPassword(ecardbDataSourceProperties().getPassword());
        return dataSource;
        //return ecardbDataSourceProperties().initializeDataSourceBuilder()
          //      .type(HikariDataSource.class).build();
    }

    @Bean(name = "ecardDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
            ecardDbEntityManagerFactory(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("ecardDbDataSource") DataSource dataSource
            ) {
        return builder
                .dataSource(dataSource)
                .packages("com.etz.merchanttransactionsync.model.ecarddb")
                .persistenceUnit("ecarddb")
                .build();
    }

    @Bean(name = "ecardDbTransactionManager")
    public PlatformTransactionManager ecardDbTransactionManager(
            @Qualifier("ecardDbEntityManagerFactory") EntityManagerFactory ecardDbEntityManagerFactory
    ) {
        return new JpaTransactionManager(ecardDbEntityManagerFactory);
    }
}

