package com.uhu.AGI.controllers;

import com.uhu.AGI.model.Review;
import com.uhu.AGI.model.ReviewDTO;
import com.uhu.AGI.services.ReviewService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author juald
 */
@Controller
@RequestMapping("/review")
public class ReviewController
{

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/home")
    public ModelAndView reviewHome(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<Review> reviewPage = reviewService.getReviews(page, size);
        ModelAndView modelAndView = new ModelAndView("review/home");
        modelAndView.addObject("title", "Gestión de Reseñas");
        modelAndView.addObject("description", "Bienvenido a la sección de gestión de reseñas.");
        modelAndView.addObject("reviews", reviewPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", reviewPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateReview(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("review/updateReview");
        Review review = reviewService.getReviewById(id);
        modelAndView.addObject("review", review);
        modelAndView.addObject("title", "Modificar Reseña");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateReviewSubmit(@ModelAttribute Review review)
    {
        ModelAndView modelAndView = new ModelAndView("review/updateReview");
        try {
            reviewService.updateReview(review);
            modelAndView.addObject("successMessage", "Reseña actualizada correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al actualizar la reseña: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id, RedirectAttributes redirectAttributes)
    {       
        try {
            Review review = reviewService.getReviewById(id);
            reviewService.deleteReview(review);
            redirectAttributes.addFlashAttribute("successMessage", "Reseña eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar la reseña: " + e.getMessage());
        }
        return "redirect:/review/search";
    }

    @GetMapping("/create")
    public ModelAndView createReview()
    {
        ModelAndView modelAndView = new ModelAndView("review/createReview");
        modelAndView.addObject("review", new Review());
        modelAndView.addObject("title", "Crear Nueva Reseña");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createReviewSubmit(@ModelAttribute Review review, ModelAndView modelAndView)
    {
        modelAndView.setViewName("review/createReview");
        try {
            reviewService.saveReview(review);
            modelAndView.addObject("successMessage", "Reseña creada correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al crear la reseña: " + e.getMessage());
        }
        modelAndView.addObject("review", review);
        modelAndView.addObject("title", "Crear Nueva Reseña");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchReviews(
            @RequestParam(required = false) String user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        ModelAndView modelAndView = new ModelAndView("review/home");
        Page<Review> reviewPage;

        reviewPage = reviewService.searchReviewByUser(user, page, size);

        modelAndView.addObject("title", "Gestión de Reseñas");
        modelAndView.addObject("description", "Resultados de la búsqueda de reseñas.");
        modelAndView.addObject("reviews", reviewPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", reviewPage.getTotalPages());
        modelAndView.addObject("searchUser", user);
        return modelAndView;
    }

    @GetMapping("/searchByUserAndDate")
    public ModelAndView getReviewsByUserAndDate(
            @RequestParam String userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date)
    {
        ModelAndView modelAndView = new ModelAndView("review/reviewsForUserAndDate");
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByUserAndDate(userId, date);
            modelAndView.addObject("reviews", reviews);
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al buscar reseñas: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/searchByKeyword")
    public ModelAndView searchReviews(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String keywords,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        ModelAndView modelAndView = new ModelAndView("review/searchByKeyword");
        Page<Review> reviewsPage;

        if (keyword != null) {
            reviewsPage = reviewService.searchReviewsByKeyword(keyword, page, size);
        } else if (keywords != null) {
            reviewsPage = reviewService.searchReviewsByKeywords(keywords, page, size);
        } else {
            reviewsPage = Page.empty(); // No hay búsqueda
        }

        modelAndView.addObject("reviewsPage", reviewsPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", reviewsPage.getTotalPages());
        modelAndView.addObject("totalItems", reviewsPage.getTotalElements());

        return modelAndView;
    }

}
