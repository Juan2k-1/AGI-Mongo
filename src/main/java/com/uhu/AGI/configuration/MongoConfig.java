package com.uhu.AGI.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Configuration
public class MongoConfig
{

    // Configurar MongoClient con la URI de conexión
    @Bean
    public MongoClient mongoClient()
    {
        return MongoClients.create("mongodb://localhost:27017/");
    }

    // Crear el MongoDatabaseFactory usando MongoClient
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory()
    {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), "yelp"); // Nombre de la base de datos
    }

    // Configurar MongoTemplate con el MongoDatabaseFactory
    @Bean
    public MongoTemplate mongoTemplate()
    {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory());

        // Configurar el convertidor
        MappingMongoConverter converter = (MappingMongoConverter) mongoTemplate.getConverter();

        // Registrar las conversiones personalizadas
        MongoCustomConversions customConversions = new MongoCustomConversions(
                Arrays.asList(new StringToLocalDateTimeConverter(), new LocalDateTimeToStringConverter())
        );
        converter.setCustomConversions(customConversions);

        // Configurar el convertidor para que aplique las conversiones
        converter.afterPropertiesSet();

        return mongoTemplate;
    }

}
