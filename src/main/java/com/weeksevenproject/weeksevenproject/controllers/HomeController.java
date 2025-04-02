package com.weeksevenproject.weeksevenproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weeksevenproject.weeksevenproject.dto.TaskDTO;
import com.weeksevenproject.weeksevenproject.models.Task;
import com.weeksevenproject.weeksevenproject.services.TaskService;
import com.weeksevenproject.weeksevenproject.utils.PaginatedResult;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private TaskService taskService;


    @GetMapping("/")
    public String index( @RequestParam(defaultValue = "5") int pageSize, @RequestParam(required = false) String nextPageToken, Model model) {
        PaginatedResult<Task> result = taskService.getTasks(pageSize, nextPageToken);
        
        model.addAttribute("tasks", result.getItems());
        
        if (result.hasNextPage()) {
            model.addAttribute("nextPageToken", result.getNextPageToken());
        }
        
        return "index";
    }

    @GetMapping("/addtask")
    public String addtask(Model model) {
        model.addAttribute("taskDTO", new TaskDTO());
        return "addtask";
    }

    @GetMapping("/edit/{id}")
    public String updatetask(@PathVariable("id") String id, Model model) {
        Task task = taskService.findTaskById(id);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(task.getTitle());
        taskDTO.setSummary(task.getSummary());
        taskDTO.setContent(task.getContent());
        model.addAttribute("taskDTO", taskDTO);
        model.addAttribute("task", task);
        return "updatetask";
    }

    @GetMapping("/task/{id}")
    public String viewtask(@PathVariable String id, Model model) {
        Task task = taskService.findTaskById(id);
        model.addAttribute("task", task);
        return "viewtask";
    }

    
    
    
}
