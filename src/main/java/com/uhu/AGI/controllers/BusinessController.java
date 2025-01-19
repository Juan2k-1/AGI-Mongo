package com.uhu.AGI.controllers;

import com.uhu.AGI.model.Business;
import com.uhu.AGI.model.InvoiceDTO;
import com.uhu.AGI.services.BusinessService;
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
@RequestMapping("/business")
public class BusinessController
{

    @Autowired
    private BusinessService businessService;

    @GetMapping("/home")
    public ModelAndView businessHome(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<Business> businessPage = businessService.getBusinesses(page, size);
        ModelAndView modelAndView = new ModelAndView("business/home");
        modelAndView.addObject("title", "Gestión de Negocios");
        modelAndView.addObject("description", "Bienvenido a la sección de gestión de negocios.");
        modelAndView.addObject("businesses", businessPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", businessPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateBusiness(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("business/updateBusiness");
        Business business = businessService.getBusinessById(id);
        modelAndView.addObject("business", business);
        modelAndView.addObject("title", "Modificar Negocio");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateBusinessSubmit(@ModelAttribute Business business)
    {
        ModelAndView modelAndView = new ModelAndView("business/updateBusiness");
        try {
            businessService.updateBusiness(business);
            modelAndView.addObject("successMessage", "Negocio actualizado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al actualizar el negocio: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteBusiness(@PathVariable String id,
            RedirectAttributes redirectAttributes)
    {
        try {
            Business business = businessService.getBusinessById(id);
            businessService.deleteBusiness(business);
            redirectAttributes.addFlashAttribute("successMessage", "Negocio eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el negocio: " + e.getMessage());
        }
        return "redirect:/business/search";
    }

    @GetMapping("/create")
    public ModelAndView createBusiness()
    {
        ModelAndView modelAndView = new ModelAndView("business/createBusiness");
        modelAndView.addObject("business", new Business());
        modelAndView.addObject("title", "Crear Nuevo Negocio");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createBusinessSubmit(@ModelAttribute Business business, ModelAndView modelAndView)
    {
        modelAndView.setViewName("business/createBusiness");
        try {
            businessService.saveBusiness(business);
            modelAndView.addObject("successMessage", "Negocio creado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al crear el negocio: " + e.getMessage());
        }
        modelAndView.addObject("business", business);  // Mantén el negocio que el usuario ingresó en el formulario
        modelAndView.addObject("title", "Crear Nuevo Negocio");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchBusinesses(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        ModelAndView modelAndView = new ModelAndView("business/searchBusinessesByName");
        Page<Business> businessPage;

        businessPage = businessService.searchBusinessesByName(name, page, size);      

        modelAndView.addObject("title", "Buscar negocio por nombre");
        modelAndView.addObject("description", "Resultados de la búsqueda de negocios.");
        modelAndView.addObject("businesses", businessPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", businessPage.getTotalPages());
        modelAndView.addObject("searchName", name); // Para mantener el valor en el campo de búsqueda
        return modelAndView;
    }

    @GetMapping("/invoices")
    public ModelAndView getInvoicesForBusiness(@RequestParam String businessId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date)
    {
        ModelAndView modelAndView = new ModelAndView("business/invoices");
        try {
            List<InvoiceDTO> invoices = businessService.getInvoicesForBusiness(businessId, date);
            modelAndView.addObject("invoices", invoices);
            modelAndView.addObject("successMessage", "Facturas encontradas correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al buscar facturas: " + e.getMessage());
        }

        return modelAndView;
    }

    @GetMapping("/topRatedBusinesses")
    public ModelAndView getTopRatedBusinessesByCategory(@RequestParam String category)
    {
        ModelAndView modelAndView = new ModelAndView("business/topRatedBusinesses");
        List<Business> businesses = businessService.getTopRatedBusinessesByCategory(category);
        modelAndView.addObject("businesses", businesses);
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @GetMapping("/mostReviewedBusinesses")
    public ModelAndView getBusinessesWithMostReviewsByCategory(@RequestParam String category)
    {
        ModelAndView modelAndView = new ModelAndView("business/mostReviewedBusinesses");
        List<Business> businesses = businessService.getBusinessesWithMostReviewsByCategory(category);
        modelAndView.addObject("businesses", businesses);
        modelAndView.addObject("category", category);  // Para mostrar la categoría en la vista
        return modelAndView;
    }

}
