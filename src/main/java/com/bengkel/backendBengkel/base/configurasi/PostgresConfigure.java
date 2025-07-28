package com.bengkel.backendBengkel.base.configurasi;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {
            "com.bengkel.backendBengkel.base.repository",
            "com.bengkel.backendBengkel.bengkel.repository",
            "com.bengkel.backendBengkel.employeeModule.repository"
        },
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@EntityScan(
        basePackages = {
            "com.bengkel.backendBengkel.bengkel.model",
            "com.bengkel.backendBengkel.employeeModule.model"
        }
)
public class PostgresConfigure {

}
