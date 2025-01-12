package com.uhu.AGI.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Component
public class SchemaSetupRunner implements CommandLineRunner
{

    private final MongoDBSchemaValidator schemaValidator;

    @Autowired
    public SchemaSetupRunner(MongoDBSchemaValidator schemaValidator)
    {
        this.schemaValidator = schemaValidator;
    }

    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("Configurando esquema de validacion en MongoDB...");

        try {
            schemaValidator.crearEsquemaValidacionBusiness();
            System.out.println("Esquema de validacion configurado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al configurar el esquema de validacion: " + e.getMessage());
        }
    }

}
