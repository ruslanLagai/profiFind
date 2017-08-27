package ru.home.profi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.home.profi.entity.Profile;
import ru.home.profi.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("profile", new Profile());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String profile(RedirectAttributes attributes, @Valid Profile profile, Errors errors) {
        //model.addAttribute("credentials", credentials);
        if (errors.hasErrors())
            return "login";

        Profile currentProfile = userService.findByUsername(profile.getUsername());
        if (currentProfile == null) {

            return "login";
        }
        attributes.addAttribute("username", currentProfile.getUsername());
        String u = currentProfile.getUsername();
        attributes.addFlashAttribute("profile", currentProfile);
        return "redirect:/admin/{username}";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String userProfile(@PathVariable("username") String username, Model model) {
        if (!model.containsAttribute("profile"))
            model.addAttribute("profile", userService.findByUsername(username));
        return "profile";
    }
}
