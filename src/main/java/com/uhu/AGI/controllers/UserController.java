package com.uhu.AGI.controllers;

import com.uhu.AGI.model.User;
import com.uhu.AGI.services.UserService;
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
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;
    
    @GetMapping("/home")
    public ModelAndView userHome(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<User> userPage = userService.getUsers(page, size);
        ModelAndView modelAndView = new ModelAndView("user/home");
        modelAndView.addObject("title", "Gestión de Usuarios");
        modelAndView.addObject("description", "Bienvenido a la sección de gestión de usuarios.");
        modelAndView.addObject("users", userPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", userPage.getTotalPages());
        return modelAndView;
    }
    
    @GetMapping("/update/{id}")
    public ModelAndView updateUser(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("user/updateUser");
        User user = userService.getUserById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("title", "Modificar Usuario");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateUserSubmit(@ModelAttribute User user)
    {
        ModelAndView modelAndView = new ModelAndView("user/updateUser");
        try {
            userService.updateUser(user);
            modelAndView.addObject("successMessage", "Usuario actualizado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al actualizar el usuario: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable String id)
    {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/home");
        try {
            User user = userService.getUserById(id);
            userService.deleteUser(user);
            modelAndView.addObject("successMessage", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al eliminar el usuario: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createUser()
    {
        ModelAndView modelAndView = new ModelAndView("user/createUser");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("title", "Crear Nuevo Usuario");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createUserSubmit(@ModelAttribute User user, ModelAndView modelAndView)
    {
        modelAndView.setViewName("user/createUser");
        try {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Usuario creado correctamente.");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", "Error al crear el usuario: " + e.getMessage());
        }
        modelAndView.addObject("user", user);  
        modelAndView.addObject("title", "Crear Nuevo Usuario");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchUsers(
            @RequestParam(required = false) String user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        ModelAndView modelAndView = new ModelAndView("user/home");
        Page<User> userPage;

        if (user != null && !user.isEmpty()) {
            userPage = userService.searchUserByName(user, page, size);
        } else {
            userPage = userService.getUsers(page, size);
        }

        modelAndView.addObject("title", "Gestión de Usuarios");
        modelAndView.addObject("description", "Resultados de la búsqueda de usuarios.");
        modelAndView.addObject("users", userPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", userPage.getTotalPages());
        modelAndView.addObject("searchUser", user); 
        return modelAndView;
    }
}
