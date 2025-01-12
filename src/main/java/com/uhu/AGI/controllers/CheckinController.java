package com.uhu.AGI.controllers;


import com.uhu.AGI.model.Checkin;
import com.uhu.AGI.services.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author juald
 */
@Controller
@RequestMapping("/checkin")
public class CheckinController
{
    @Autowired
    private CheckinService checkinService;

    @GetMapping("/home")
    public ModelAndView checkinHome(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<Checkin> checkinPage = checkinService.getCheckins(page, size);
        ModelAndView modelAndView = new ModelAndView("checkin/home");
        modelAndView.addObject("title", "Gestión de Facturas");
        modelAndView.addObject("description", "Bienvenido a la sección de gestión de facturas.");
        modelAndView.addObject("checkins", checkinPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", checkinPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateCheckin(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("checkin/updateCheckin");
        Checkin checkin = checkinService.getCheckinById(id);
        modelAndView.addObject("checkin", checkin);
        modelAndView.addObject("title", "Modificar Factura");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateCheckinSubmit(@ModelAttribute Checkin checkin)
    {
        ModelAndView modelAndView = new ModelAndView("checkin/updateCheckin");
        try {
            checkinService.updateCheckin(checkin);
            modelAndView.addObject("successMessage", "Factura actualizada correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al actualizar la factura: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCheckin(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("redirect:/checkin/home");
        try {
            Checkin checkin = checkinService.getCheckinById(id);
            checkinService.deleteCheckin(checkin);
            modelAndView.addObject("successMessage", "Factura eliminada correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al eliminar la factura: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createCheckin()
    {
        ModelAndView modelAndView = new ModelAndView("checkin/createCheckin");
        modelAndView.addObject("checkin", new Checkin());
        modelAndView.addObject("title", "Crear Nuevo Factura");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createCheckinSubmit(@ModelAttribute Checkin checkin, ModelAndView modelAndView)
    {
        modelAndView.setViewName("checkin/createCheckin");
        try {
            checkinService.saveCheckin(checkin);
            modelAndView.addObject("successMessage", "Factura creado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al crear la factura: " + e.getMessage());
        }
        modelAndView.addObject("checkin", checkin);  
        modelAndView.addObject("title", "Crear Nuevo Factura");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchCheckins(
            @RequestParam(required = false) String business,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        ModelAndView modelAndView = new ModelAndView("checkin/home");
        Page<Checkin> checkinPage;

        if (business != null && !business.isEmpty()) {
            checkinPage = checkinService.searchCheckinByName(business, page, size);
        } else {
            checkinPage = checkinService.getCheckins(page, size);
        }

        modelAndView.addObject("title", "Gestión de Facturas");
        modelAndView.addObject("description", "Resultados de la búsqueda de facturas.");
        modelAndView.addObject("checkins", checkinPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", checkinPage.getTotalPages());
        modelAndView.addObject("searchBusiness", business); 
        return modelAndView;
    }
}
