package stormsprid.emilspring.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import stormsprid.emilspring.Entity.Category;
import stormsprid.emilspring.Entity.Task;
import stormsprid.emilspring.Entity.User;
import stormsprid.emilspring.Repository.TaskRepository;
import stormsprid.emilspring.Service.TaskService;
import stormsprid.emilspring.Service.CategoryService;
import stormsprid.emilspring.Repository.UserRepository;


@Controller
@AllArgsConstructor
@NoArgsConstructor
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("add")
    public String showAddTask(Model model){
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("task", new Task());
        model.addAttribute("users", userRepository.findAll());
        return "add";
    }
    @PostMapping("add")
    public String createTask(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam("categoryId") Long categoryId,
                             Task task,
                             @RequestParam("userId") Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("Unknown login"));
        task.setUser(currentUser);

        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));

        task.setCategory(category);
        taskService.saveTask(task);
        return "redirect:/admin/main";
    }
    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable("id") Long id, Model model) {
        Task task = taskService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "edit";  // предполагается, что шаблон находится в resources/templates/edit.html
    }

    // Метод для сохранения отредактированной задачи
    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable("id") Long id, @ModelAttribute("task") Task task, @AuthenticationPrincipal UserDetails userDetails) {
        task.setId(id);
        User currentUser =  userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Unknown login"));
        task.setUser(currentUser);
        Optional<Category> category = categoryService.findCategoryById(task.getCategory().getId());
        task.setCategory(category.orElse(null));
        taskService.saveTask(task);
        return "redirect:/";
    }


}