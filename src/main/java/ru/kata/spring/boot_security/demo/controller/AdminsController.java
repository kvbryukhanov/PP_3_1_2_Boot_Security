package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String showAll(ModelMap model) {
        model.addAttribute("users", userService.index());
        return "index";
    }

    @GetMapping(value = "/show")
    public String showOne(ModelMap model, @RequestParam int id) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", userService.getUserRoles(id));
        return "show";
    }

    @GetMapping(value = "/create")
    public String create(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("user") @Valid User user,
                       BindingResult bindingResult,
                       ModelMap model,
                       @RequestParam("roleIds") List<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "create";
        }

        userService.save(user, roleIds);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/edit")
    public String edit(ModelMap model, @RequestParam int id) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         ModelMap model,
                         @RequestParam int id,
                         @RequestParam(value = "roleIds", required = false)
                         List<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "edit";
        }

        userService.update(id, user, roleIds);
        return "redirect:/admin/show?id=" + id;
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam int id) {
        userService.delete(id);
        return "redirect:/admin/";
    }
}