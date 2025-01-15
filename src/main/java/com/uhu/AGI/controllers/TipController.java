package com.uhu.AGI.controllers;

import com.uhu.AGI.model.Tip;
import com.uhu.AGI.services.TipService;
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
@RequestMapping("/tip")
public class TipController
{

    @Autowired
    private TipService tipService;

    @GetMapping("/home")
    public ModelAndView tipHome(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<Tip> tipPage = tipService.getTips(page, size);
        ModelAndView modelAndView = new ModelAndView("tip/home");
        modelAndView.addObject("title", "Gestión de Tips");
        modelAndView.addObject("description", "Bienvenido a la sección de gestión de tips.");
        modelAndView.addObject("tips", tipPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", tipPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateTip(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("tip/updateTip");
        Tip tip = tipService.getTipById(id);
        modelAndView.addObject("tip", tip);
        modelAndView.addObject("title", "Modificar Tip");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateTipSubmit(@ModelAttribute Tip tip)
    {
        ModelAndView modelAndView = new ModelAndView("tip/updateTip");
        try {
            tipService.updateTip(tip);
            modelAndView.addObject("successMessage", "Tip actualizado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al actualizar el tip: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteTip(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("redirect:/tip/home");
        try {
            Tip tip = tipService.getTipById(id);
            tipService.deleteTip(tip);
            modelAndView.addObject("successMessage", "Tip eliminado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al eliminar el tip: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createTip()
    {
        ModelAndView modelAndView = new ModelAndView("tip/createTip");
        modelAndView.addObject("tip", new Tip());
        modelAndView.addObject("title", "Crear Nuevo Tip");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createTipSubmit(@ModelAttribute Tip tip, ModelAndView modelAndView)
    {
        modelAndView.setViewName("tip/createTip");
        try {
            tipService.saveTip(tip);
            modelAndView.addObject("successMessage", "Tip creado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al crear el tip: " + e.getMessage());
        }
        modelAndView.addObject("tip", tip);
        modelAndView.addObject("title", "Crear Nuevo Tip");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchTips(
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        ModelAndView modelAndView = new ModelAndView("tip/home");
        Page<Tip> tipPage;

        if (text != null && !text.isEmpty()) {
            tipPage = tipService.searchByText(text, page, size);
        } else {
            tipPage = tipService.getTips(page, size);
        }

        modelAndView.addObject("title", "Gestión de Tips");
        modelAndView.addObject("description", "Resultados de la búsqueda de tips.");
        modelAndView.addObject("tips", tipPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", tipPage.getTotalPages());
        modelAndView.addObject("searchText", text);
        return modelAndView;
    }
}
