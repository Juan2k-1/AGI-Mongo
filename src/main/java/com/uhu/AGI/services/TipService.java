package com.uhu.AGI.services;

import com.uhu.AGI.model.Tip;
import com.uhu.AGI.repositories.TipRepository;
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
public class TipService
{

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private Validator validator;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    // Listar todos los tips
    public List<Tip> findAllTips()
    {
        return this.tipRepository.findAll();
    }

    // Obtener tips paginados
    public Page<Tip> getTips(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return tipRepository.findAll(pageable);
    }

    // Obtener un tip por ID
    public Tip getTipById(String id)
    {
        return tipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tip no encontrado"));
    }

    // Guardar un nuevo tip
    @Transactional
    public void saveTip(@Valid Tip tip)
    {
        // Validar el objeto
        Set<ConstraintViolation<Tip>> violations = validator.validate(tip);

        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<Tip> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException("Errores de validación: " + errors.toString(), violations);
        }

        this.tipRepository.save(tip);
    }

    // Actualizar un tip existente
    @Transactional
    public void updateTip(@Valid Tip tip)
    {
        try {
            Optional<Tip> result = this.tipRepository.findById(tip.getId());

            if (result.isPresent()) {
                Tip existingTip = result.get();

                // Actualizar solo los campos que no sean nulos o vacíos
                if (tip.getUserId() != null && !tip.getUserId().isBlank()) {
                    existingTip.setUserId(tip.getUserId());
                }
                if (tip.getBusinessId() != null && !tip.getBusinessId().isBlank()) {
                    existingTip.setBusinessId(tip.getBusinessId());
                }
                if (tip.getText() != null && !tip.getText().isBlank()) {
                    existingTip.setText(tip.getText());
                }
                if (tip.getDate() != null) {
                    existingTip.setDate(tip.getDate());
                }
                if (tip.getComplimentCount() >= 0) {
                    existingTip.setComplimentCount(tip.getComplimentCount());
                }

                this.tipRepository.save(existingTip);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error actualizando tip", e);
            throw e;
        }
    }

    // Eliminar un tip
    @Transactional
    public void deleteTip(@Valid Tip tip)
    {
        try {
            if (this.tipRepository.existsById(tip.getId())) {
                this.tipRepository.delete(tip);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando tip", e);
            throw e;
        }
    }

    /**
     * Busca tips que contengan un texto específico.
     *
     * @param text Texto a buscar en los tips.
     * @param page Página de resultados (base 0).
     * @param size Tamaño de la página.
     * @return Página de tips que contienen el texto buscado.
     */
    public Page<Tip> searchByText(String text, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return tipRepository.findByTextContainingIgnoreCase(text, pageable);
    }

}
