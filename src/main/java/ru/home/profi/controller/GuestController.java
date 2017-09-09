package ru.home.profi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.home.profi.entity.Company;
import ru.home.profi.entity.Profile;
import ru.home.profi.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class GuestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("employee", new Profile());
        model.addAttribute("employer", new Company());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createEntity(RedirectAttributes attributes, @Valid Profile profile,
                               @Valid Company company, Errors errors) {

        return "redirect:/admin/{username}";
    }

}
