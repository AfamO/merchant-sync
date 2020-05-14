/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.db.config.ecarddb;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author afam.okonkwo
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "ecardDbEntityManagerFactory",
        basePackages = {"com.etz.merchanttransactionsync.repository.ecarddb"}
)
public class EcardDbConfig {

    @Bean(name = "ecardDbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ecardDbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
            ecardDbEntityManagerFactory(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("ecardDbDataSource") DataSource dataSource
            ) {
        return builder
                .dataSource(dataSource)
                .packages("com.etz.transaction.synchronizer.model.WebConnectTransactionLog")
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

