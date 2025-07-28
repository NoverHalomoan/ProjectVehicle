package com.bengkel.backendBengkel.base.configurasi;

import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.bengkel.backendBengkel.base.untilize.EnviromentGetData;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(
        basePackages = {
            "com.bengkel.backendBengkel.messageModule.repository"
        },
        mongoTemplateRef = "mongoTemplate"
)
public class MongoConfig {

    private EnviromentGetData enviromentGetData;

    public MongoConfig(EnviromentGetData enviromentGetData) {
        this.enviromentGetData = enviromentGetData;

    }

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(enviromentGetData.getConnectionString());
        MongoClientSettings settings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(connectionString).build();

        return MongoClients.create(settings);
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), enviromentGetData.getDatabaseNames());
    }
}
