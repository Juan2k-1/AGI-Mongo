package com.uhu.AGI.services;

import com.uhu.AGI.model.Business;
import com.uhu.AGI.model.InvoiceDTO;
import com.uhu.AGI.model.InvoiceDetailsDTO;
import com.uhu.AGI.repositories.BusinessRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

/**
 * Service class for managing business.
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Service
@Transactional
public class BusinessService
{

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Validator validator;

    private static final Logger logger = Logger.getLogger(BusinessService.class.getName());

    public List<Business> findAllBusiness()
    {
        return this.businessRepository.findAll();
    }

    public Page<Business> getBusinesses(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return businessRepository.findAll(pageable);
    }

    public Business getBusinessById(String id)
    {
        return businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado"));
    }

    /**
     * Save a new business.
     *
     * @param business
     */
    @Transactional
    public void saveBusiness(@Valid Business business)
    {
        // Realiza la validación del objeto
        Set<ConstraintViolation<Business>> violations = validator.validate(business);

        // Si hay errores de validación, lanza una excepción
        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<Business> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException("Errores de validacion: " + errors.toString(), violations);
        }

        this.businessRepository.save(business);
    }

    /**
     * Update the business.
     *
     * @param business
     */
    @Transactional
    public void updateBusiness(@Valid Business business)
    {
        try {
            Optional<Business> result = this.businessRepository.findById(business.getId());

            if (result.isPresent()) {
                Business existingBusiness = result.get();

                // Actualizar solo los campos que no sean nulos o vacíos
                if (!business.getName().isBlank()) {
                    existingBusiness.setName(business.getName());
                }
                if (!business.getAddress().isBlank()) {
                    existingBusiness.setAddress(business.getAddress());
                }
                if (!business.getCity().isBlank()) {
                    existingBusiness.setCity(business.getCity());
                }
                if (!business.getState().isBlank()) {
                    existingBusiness.setState(business.getState());
                }
                if (!business.getPostalCode().isBlank()) {
                    existingBusiness.setPostalCode(business.getPostalCode());
                }
                if (business.getLatitude() != 0) {
                    existingBusiness.setLatitude(business.getLatitude());
                }
                if (business.getLongitude() != 0) {
                    existingBusiness.setLongitude(business.getLongitude());
                }
                if (business.getStars() >= 0 && business.getStars() <= 5) {
                    existingBusiness.setStars(business.getStars());
                }
                if (business.getReviewCount() >= 0) {
                    existingBusiness.setReviewCount(business.getReviewCount());
                }
                if (business.getIsOpen() == 0 || business.getIsOpen() == 1) {
                    existingBusiness.setIsOpen(business.getIsOpen());
                }

                this.businessRepository.save(existingBusiness);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating business", e);
            throw e;
        }
    }

    /**
     * Delete a business
     *
     * @param business
     */
    @Transactional
    public void deleteBusiness(@Valid Business business
    )
    {
        try {
            if (this.businessRepository.existsById(business.getId())) {
                this.businessRepository.delete(business);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting business", e);
            throw e;
        }
    }

    /**
     *
     * @param name
     * @param page
     * @param size
     * @return
     */
    @Transactional
    public Page<Business> searchBusinessesByName(String name, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return businessRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional
    public List<InvoiceDTO> getInvoicesForBusiness(String businessId, LocalDateTime targetDate)
    {
        // Etapa 1: Filtrar negocios por business_id
        AggregationOperation matchBusinessId = match(Criteria.where("business_id").is(businessId));

        // Etapa 2: Hacer lookup con la colección "checkin"
        LookupOperation lookupInvoices = LookupOperation.newLookup()
                .from("checkin")
                .localField("business_id")
                .foreignField("business_id")
                .as("invoices");

        // Etapa 3: Desenrollar el array de facturas
        UnwindOperation unwindInvoices = Aggregation.unwind("invoices");

        // Etapa 4: Filtrar facturas por fecha
        AggregationOperation matchByDate = match(Criteria.where("invoices.date").in(targetDate));

        // Etapa 5: Seleccionar los campos relevantes
        AggregationOperation projectFields = project("name", "address")
                .and("invoices").as("invoiceDetails");

        // Construir la agregación
        Aggregation aggregation = newAggregation(
                matchBusinessId,
                lookupInvoices,
                unwindInvoices,
                matchByDate,
                projectFields
        );

        // Ejecutar la consulta
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "business", Document.class);
        /*results.forEach(doc -> {
            System.out.println(doc.toJson());
        });*/

        // Mapeo de los resultados a InvoiceDTO
        return results.getMappedResults().stream().map(doc -> {
            InvoiceDTO dto = new InvoiceDTO();

            // Crear el DTO para los detalles de la factura
            InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();

            // Acceder al campo invoiceDetails que contiene la información de la factura
            Object invoiceDetails = doc.get("invoiceDetails");

            if (invoiceDetails instanceof Document) {
                Document invoiceDoc = (Document) invoiceDetails;

                // Obtener el ObjectId y convertirlo a String
                ObjectId invoiceId = invoiceDoc.getObjectId("_id");  // Obtienes el ObjectId
                if (invoiceId != null) {
                    invoiceDetailsDTO.setInvoiceId(invoiceId.toString());  // Convertir a String
                }

                // Manejar invoiceDates, que puede ser una lista o un solo string
                Object invoiceDates = invoiceDoc.get("date");
                if (invoiceDates instanceof String) {
                    // Si es un solo string, conviértelo en una lista con un único elemento
                    invoiceDetailsDTO.setInvoiceDates(List.of((String) invoiceDates));
                } else if (invoiceDates instanceof List) {
                    // Si ya es una lista, simplemente asígnala
                    invoiceDetailsDTO.setInvoiceDates((List<String>) invoiceDates);
                } else {
                    // Si es nulo o no esperado, inicializa como lista vacía
                    invoiceDetailsDTO.setInvoiceDates(Collections.emptyList());
                }

                // Asignar Business ID (ID del negocio) desde invoiceDetails
                dto.setBusinessId(invoiceDoc.getString("business_id"));
            }

            // Asignar los demás campos desde el documento principal
            dto.setName(doc.getString("name"));
            dto.setAddress(doc.getString("address"));

            // Asignar el DTO de invoiceDetails
            dto.setInvoiceDetails(invoiceDetailsDTO);

            return dto;
        }).toList();
    }

    public List<Business> getTopRatedBusinessesByCategory(String category)
    {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("categories").is(category)), // Filtra por categoría
                Aggregation.sort(Sort.by(Sort.Order.desc("stars"))), // Ordena por la calificación (stars)
                Aggregation.limit(10) // Limita a los 10 primeros negocios
        );

        AggregationResults<Business> results = mongoTemplate.aggregate(aggregation, "business", Business.class);
        return results.getMappedResults();
    }

    public List<Business> getBusinessesWithMostReviewsByCategory(String category)
    {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("categories").in(category)), // Filtra por la categoría
                Aggregation.sort(Sort.by(Sort.Order.desc("review_count"))), // Ordena por el número de reseñas
                Aggregation.limit(10) // Limita a los 10 negocios con más reseñas
        );

        AggregationResults<Business> results = mongoTemplate.aggregate(aggregation, "business", Business.class);
        return results.getMappedResults();
    }

}
