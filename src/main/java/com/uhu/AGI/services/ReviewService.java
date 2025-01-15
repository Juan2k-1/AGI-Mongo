package com.uhu.AGI.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.uhu.AGI.model.Review;
import com.uhu.AGI.model.ReviewDTO;
import com.uhu.AGI.repositories.ReviewRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.bson.Document;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author juald
 */
@Service
@Transactional
public class ReviewService
{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Validator validator;

    private static final Logger logger = Logger.getLogger(ReviewService.class.getName());

    // Listar todas las reseñas
    public List<Review> findAllReviews()
    {
        return this.reviewRepository.findAll();
    }

    // Obtener reseñas paginadas
    public Page<Review> getReviews(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findAll(pageable);
    }

    // Obtener una reseña por ID
    public Review getReviewById(String id)
    {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
    }

    // Guardar una nueva reseña
    @Transactional
    public void saveReview(@Valid Review review)
    {
        // Validar el objeto
        Set<ConstraintViolation<Review>> violations = validator.validate(review);

        // Si hay errores de validación, lanza una excepción
        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<Review> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException("Errores de validación: " + errors.toString(), violations);
        }

        this.reviewRepository.save(review);
    }

    // Actualizar una reseña existente
    @Transactional
    public void updateReview(@Valid Review review)
    {
        try {
            Optional<Review> result = this.reviewRepository.findById(review.getId());

            if (result.isPresent()) {
                Review existingReview = result.get();

                // Actualizar solo los campos que no sean nulos o vacíos
                if (review.getId() != null && !review.getId().isBlank()) {
                    existingReview.setId(review.getId());
                }
                if (review.getUserId() != null && !review.getUserId().isBlank()) {
                    existingReview.setUserId(review.getUserId());
                }
                if (review.getBusinessId() != null && !review.getBusinessId().isBlank()) {
                    existingReview.setBusinessId(review.getBusinessId());
                }
                if (review.getStars() >= 1 && review.getStars() <= 5) {
                    existingReview.setStars(review.getStars());
                }
                if (review.getUseful() >= 0) {
                    existingReview.setUseful(review.getUseful());
                }
                if (review.getFunny() >= 0) {
                    existingReview.setFunny(review.getFunny());
                }
                if (review.getCool() >= 0) {
                    existingReview.setCool(review.getCool());
                }
                if (review.getText() != null && !review.getText().isBlank()) {
                    existingReview.setText(review.getText());
                }
                if (review.getDate() != null) {
                    existingReview.setDate(review.getDate());
                }

                this.reviewRepository.save(existingReview);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error actualizando reseña", e);
            throw e;
        }
    }

    // Eliminar una reseña
    @Transactional
    public void deleteReview(@Valid Review review)
    {
        try {
            if (this.reviewRepository.existsById(review.getId())) {
                this.reviewRepository.delete(review);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando reseña", e);
            throw e;
        }
    }

    @Transactional
    public Page<Review> searchReviewByUser(String userId, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByUserIdContainingIgnoreCase(userId, pageable);
    }

    @Transactional
    public List<ReviewDTO> getReviewsByUserAndDate(String userId, LocalDateTime date)
    {
        // Paso 1: Filtrar reseñas por user_id y fecha
        AggregationOperation matchUserAndDate = match(Criteria.where("user_id").is(userId).and("date").in(date));

        // Paso 2: Lookup para obtener información del negocio
        LookupOperation lookupBusinessDetails = LookupOperation.newLookup()
                .from("business")
                .localField("business_id")
                .foreignField("business_id")
                .as("businessDetails");

        // Paso 3: Desenrollar los detalles del negocio
        UnwindOperation unwindBusinessDetails = unwind("businessDetails");

        // Paso 4: Seleccionar campos relevantes
        AggregationOperation projectFields = Aggregation.project()
                .and("_id").as("reviewId")
                .and("user_id").as("userId")
                .and("business_id").as("businessId")
                .and("businessDetails.name").as("businessName")
                .and("text").as("reviewText")
                .and("stars").as("stars")
                .and("date").as("date");

        // Construcción de la agregación
        Aggregation aggregation = newAggregation(
                matchUserAndDate,
                lookupBusinessDetails,
                unwindBusinessDetails,
                projectFields
        );

        // Ejecución de la consulta
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "review", Document.class);

        // Mapear los resultados a DTOs
        return results.getMappedResults().stream().map(doc -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setReviewId(doc.getObjectId("reviewId").toString());
            dto.setUserId(doc.getString("userId"));
            dto.setBusinessId(doc.getString("businessId"));
            dto.setBusinessName(doc.getString("businessName"));
            dto.setReviewText(doc.getString("reviewText"));
            dto.setStars(doc.getInteger("stars"));
            String dateString = doc.getString("date");
            if (dateString != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dto.setDate(LocalDateTime.parse(dateString, formatter));
            } else {
                dto.setDate(null); // O un valor por defecto
            }

            return dto;
        }).toList();
    }

    public Page<Review> searchReviewsByKeyword(String keyword, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByTextContainingKeyword(keyword, pageable);
    }

    public Page<Review> searchReviewsByKeywords(String keywords, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByTextWithKeywordsSortedByRelevance(keywords, pageable);
    }
}
