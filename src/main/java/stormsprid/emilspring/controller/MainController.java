package stormsprid.emilspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
import stormsprid.emilspring.Entity.Task;
import stormsprid.emilspring.Repository.TaskRepository;
import stormsprid.emilspring.Service.CategoryService;
import stormsprid.emilspring.Service.TaskService;
import stormsprid.emilspring.security.UserDetailsImpl;


@Controller
public class MainController {
    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String main(@RequestParam(value = "status", required = false, defaultValue = "") String status,
                       @RequestParam(value = "categoryId", required = false) Long categoryId,
                       @RequestParam(value = "search", required = false, defaultValue = "") String search,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                       Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long userId = userDetails.getId();

        // Устанавливаем фильтры
        String statusFilter = status.equalsIgnoreCase("all") || status.isEmpty() ? null : status;

        // Создаем пагинацию
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        Page<Task> tasksPage = taskService.findTasksWithPaginationAndSearch(statusFilter, categoryId, userId, search, pageable);

        // Добавляем атрибуты в модель
        model.addAttribute("tasks", tasksPage.getContent());
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("status", status);
        model.addAttribute("category", categoryId);
        model.addAttribute("searchQuery", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tasksPage.getTotalPages());
        return "main";
    }





    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/";
    }
}
