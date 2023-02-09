package com.in28minutes.springboot.myfirstwebapp.todo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class TodoController {
    
    @Autowired
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(value="list-todos", method=RequestMethod.GET)
    public String listTodos(ModelMap model) {
        String userName = getLoggedInUserName();
        model.addAttribute("todos", todoService.findByUserName(userName)); 
        return "listTodos";
    } 
    
    @RequestMapping(value="add-todo", method=RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        String userName = getLoggedInUserName();
        model.put("todo", new Todo(0, userName, "temp", LocalDate.now(), false));
        return "todo";        
    }
    
    @RequestMapping(value="add-todo", method=RequestMethod.POST) 
    public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        String userName = getLoggedInUserName();
        todoService.addTodo(userName, todo.getDescription(), todo.getTargetDate());
        return "redirect:list-todos";        
    }
    
    @RequestMapping(value="update-todo", method=RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam("id") int id, ModelMap model) {
        Todo todo = todoService.findById(id);
        model.put("todo", todo);
        return "todo";        
    }
    
    @RequestMapping(value="update-todo", method=RequestMethod.POST) 
    public String updateTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        todoService.updateTodo(todo);
        return "redirect:list-todos";        
    }
    
    @RequestMapping(value="delete-todo", method=RequestMethod.GET) 
    public String addTodo(@RequestParam("id") int id ) {
        todoService.deleteTodo(id);
        return "redirect:list-todos";        
    }

    private String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
    
}
