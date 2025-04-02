package com.weeksevenproject.weeksevenproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.weeksevenproject.weeksevenproject.dto.TaskDTO;
import com.weeksevenproject.weeksevenproject.services.TaskService;

import jakarta.validation.Valid;



@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;


    @PostMapping("/submit")
    public String addtask(@Valid TaskDTO taskDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "addtask";
        }
        boolean success = taskService.addtask(taskDTO);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Task added successfully");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("message", "There was a problem adding the task. Try again later");
        return "redirect:/addtask";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        boolean success = taskService.deleteTask(id);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Task deleted successfully");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("message", "There was a problem deleting the task");
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateTask(@Valid TaskDTO taskDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, @RequestParam("id") String id) {
        if(bindingResult.hasErrors()) {
            return "updatetask";
        }
        boolean success = taskService.updateTask(taskDTO, id);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Task updated successfully");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("message", "There was a problem updating the task. Try again later");
        return "redirect:/updatetask";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        boolean success = taskService.completeTask(id);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Task marked as completed successfully");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("message", "There was a problem marking the task as completed. Try again later");
        return "redirect:/";
    }
    
    
}
