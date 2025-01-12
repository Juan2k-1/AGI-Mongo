package com.uhu.AGI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Juan Alberto Domínguez Vázquez
 */
@Controller
@RequestMapping("/")
public class PrincipalController
{
    @RequestMapping("/")
    public ModelAndView welcome()
    {
        ModelAndView result = new ModelAndView("home");
        result.addObject("message", "Bienvenido a la página principal.");
        return result;
    }
}
