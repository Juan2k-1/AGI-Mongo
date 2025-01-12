package com.uhu.AGI.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Component
public class MongoDBSchemaValidator
{

    @Autowired
    private MongoTemplate mongoTemplate;

    public void crearEsquemaValidacionBusiness()
    {
        mongoTemplate.executeCommand("""
            {
                "collMod": "business",
                "validator": {
                    "$jsonSchema": {
                        "bsonType": "object",
                        "required": ["business_id", "name", "city", "state", "address", "postal_code", "latitude", "longitude", "stars", "review_count", "is_open"],
                        "properties": {
                            "id": { "bsonType": "string", "description": "El ID debe ser una cadena unica generada automaticamente." },
                                     
                            "business_id": { "bsonType": "string", "description": "El businessId es obligatorio." },
                                     
                            "name": { "bsonType": "string", "description": "El nombre del negocio es obligatorio." },
                                     
                            "address": { "bsonType": "string", "description": "La direccion es obligatoria." },
                                     
                            "city": { "bsonType": "string", "description": "La ciudad es obligatoria y debe ser una cadena." },
                                     
                            "state": { "bsonType": "string", "description": "El estado es obligatorio y debe ser una cadena." },
                                     
                            "postal_code": { "bsonType": "string", "description": "El codigo postal es obligatorio y debe ser una cadena." },
                                     
                            "latitude": { "bsonType": "double", "minimum": -90, "maximum": 90, "description": "La latitud debe estar entre -90 y 90." },
                                     
                            "longitude": { "bsonType": "double", "minimum": -180, "maximum": 180, "description": "La longitud debe estar entre -180 y 180." },
                                     
                            "stars": { "bsonType": "double", "minimum": 0, "maximum": 5, "description": "Las estrellas deben estar entre 0 y 5." },
                                     
                            "review_count": { "bsonType": "int", "minimum": 0, "description": "El numero de reseñas debe ser un entero no negativo." },
                                     
                            "is_open": { "bsonType": "int", "enum": [0, 1], "description": "Indica si el negocio está abierto (1) o cerrado (0)." },
                                     
                            "attributes": { "bsonType": ["object", "null"], "description": "Los atributos pueden ser un objeto o nulos." },
                                     
                            "categories": { "bsonType": ["array", "null"], "items": { "bsonType": "string" }, "description": "Las categorias son una lista de cadenas o nulas." },
                                     
                            "hours": { "bsonType": ["object", "null"], "description": "Las horas son un mapa de pares clave-valor o nulas." }
                        }
                    }
                },
                "validationLevel": "strict",
                "validationAction": "error"
            }
        """);
    }
}
