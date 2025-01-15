package com.uhu.AGI.services;

import com.uhu.AGI.model.User;
import com.uhu.AGI.repositories.UserRepository;
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
public class UserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    // Listar todos los usuarios
    public List<User> findAllUsers()
    {
        return this.userRepository.findAll();
    }

    // Obtener usuarios paginados
    public Page<User> getUsers(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    // Obtener un usuario por ID
    public User getUserById(String id)
    {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Guardar un nuevo usuario
    @Transactional
    public void saveUser(@Valid User user)
    {
        // Validar el objeto
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException("Errores de validación: " + errors.toString(), violations);
        }

        this.userRepository.save(user);
    }

    // Actualizar un usuario existente
    @Transactional
    public void updateUser(@Valid User user)
    {
        try {
            Optional<User> result = this.userRepository.findById(user.getId());

            if (result.isPresent()) {
                User existingUser = result.get();

                // Actualizar solo los campos que no sean nulos o vacíos
                if (user.getUserId() != null && !user.getUserId().isBlank()) {
                    existingUser.setUserId(user.getUserId());
                }
                if (user.getName() != null && !user.getName().isBlank()) {
                    existingUser.setName(user.getName());
                }
                if (user.getReviewCount() >= 0) {
                    existingUser.setReviewCount(user.getReviewCount());
                }
                if (user.getYelpingSince() != null && !user.getYelpingSince().isBlank()) {
                    existingUser.setYelpingSince(user.getYelpingSince());
                }
                if (user.getUseful() >= 0) {
                    existingUser.setUseful(user.getUseful());
                }
                if (user.getFunny() >= 0) {
                    existingUser.setFunny(user.getFunny());
                }
                if (user.getCool() >= 0) {
                    existingUser.setCool(user.getCool());
                }
                if (user.getElite() != null) {
                    existingUser.setElite(user.getElite());
                }
                if (user.getFriends() != null) {
                    existingUser.setFriends(user.getFriends());
                }
                if (user.getFans() >= 0) {
                    existingUser.setFans(user.getFans());
                }
                if (user.getAverageStars() >= 0) {
                    existingUser.setAverageStars(user.getAverageStars());
                }

                // Actualizar campos de "compliment_*"
                existingUser.setComplimentHot(user.getComplimentHot());
                existingUser.setComplimentMore(user.getComplimentMore());
                existingUser.setComplimentProfile(user.getComplimentProfile());
                existingUser.setComplimentCute(user.getComplimentCute());
                existingUser.setComplimentList(user.getComplimentList());
                existingUser.setComplimentNote(user.getComplimentNote());
                existingUser.setComplimentPlain(user.getComplimentPlain());
                existingUser.setComplimentCool(user.getComplimentCool());
                existingUser.setComplimentFunny(user.getComplimentFunny());
                existingUser.setComplimentWriter(user.getComplimentWriter());
                existingUser.setComplimentPhotos(user.getComplimentPhotos());

                this.userRepository.save(existingUser);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error actualizando usuario", e);
            throw e;
        }
    }

    // Eliminar un usuario
    @Transactional
    public void deleteUser(@Valid User user)
    {
        try {
            if (this.userRepository.existsById(user.getId())) {
                this.userRepository.delete(user);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error eliminando usuario", e);
            throw e;
        }
    }

    // Buscar usuarios por nombre (paginado)
    @Transactional
    public Page<User> searchUserByName(String name, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByNameContainingIgnoreCase(name, pageable);
    }   
}
