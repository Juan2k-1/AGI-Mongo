package com.uhu.AGI.services;

import com.uhu.AGI.model.Checkin;
import com.uhu.AGI.repositories.CheckinRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author juald
 */
@Service
@Transactional
public class CheckinService
{

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private Validator validator;

    private static final Logger logger = Logger.getLogger(CheckinService.class.getName());

    // Listar todos los checkins
    public List<Checkin> findAllCheckins()
    {
        return this.checkinRepository.findAll();
    }

    // Obtener checkins paginados
    public Page<Checkin> getCheckins(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return checkinRepository.findAll(pageable);
    }

    // Obtener un checkin por ID
    public Checkin getCheckinById(String id)
    {
        return checkinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checkin no encontrado"));
    }

    // Guardar un nuevo checkin
    @Transactional
    public void saveCheckin(@Valid Checkin checkin)
    {
        // Validar el objeto
        Set<ConstraintViolation<Checkin>> violations = validator.validate(checkin);

        // Si hay errores de validación, lanza una excepción
        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<Checkin> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException("Errores de validación: " + errors.toString(), violations);
        }

        this.checkinRepository.save(checkin);
    }

    // Actualizar un checkin existente
    @Transactional
    public void updateCheckin(@Valid Checkin checkin)
    {
        try {
            Optional<Checkin> result = this.checkinRepository.findById(checkin.getId());

            if (result.isPresent()) {
                Checkin existingCheckin = result.get();

                // Actualizar solo los campos que no sean nulos o vacíos
                if (checkin.getBusinessId() != null && !checkin.getBusinessId().isBlank()) {
                    existingCheckin.setBusinessId(checkin.getBusinessId());
                }
                if (checkin.getDate() != null && !checkin.getDate().isEmpty()) {
                    existingCheckin.setDate(checkin.getDate());
                }

                this.checkinRepository.save(existingCheckin);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error actualizando checkin", e);
            throw e;
        }
    }

    // Eliminar un checkin
    @Transactional
    public void deleteCheckin(@Valid Checkin checkin)
    {
        try {
            if (this.checkinRepository.existsById(checkin.getId())) {
                this.checkinRepository.delete(checkin);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando checkin", e);
            throw e;
        }
    }
    
    @Transactional
    public Page<Checkin> searchCheckinByName(String business, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return checkinRepository.findByBusinessId(business, pageable);
    }
}
