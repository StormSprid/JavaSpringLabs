package stormsprid.emilspring.controller;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import stormsprid.emilspring.Entity.User;
import stormsprid.emilspring.Service.UserService;

@Controller
@AllArgsConstructor
public class RegistrationController {
    @Autowired
    UserService userService;
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user")@Validated User user){
        userService.SaveUser(user);
        return "redirect:/login";

    }
}
