package com.in28minutes.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class TodoService {
    private static List<Todo> todos = new ArrayList<>();
    static {
        todos.add(new Todo(1, "Paul", "Todo One", LocalDate.now().plusDays(2), false));
        todos.add(new Todo(2, "Paul", "Todo Two", LocalDate.now().plusDays(2), false));
        todos.add(new Todo(3, "Paul", "Todo Three", LocalDate.now().plusDays(2), false));
        todos.add(new Todo(4, "Paul", "Todo Four", LocalDate.now().plusDays(2), false));
        todos.add(new Todo(5, "in28minutes", "Todo Five", LocalDate.now().plusDays(2), false));
    }
    
    public List<Todo> findByUserName(String userName) {
        Predicate<Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(userName);
        return todos.stream().filter(predicate).toList();
    }
    
    public Todo findById(int id) {
        for (int i=0; i < todos.size(); i++) {
            Todo currentTodo = todos.get(i);
            if (currentTodo.getId() == id) {
                return currentTodo;
            }
        }
        return null;
    }
    
    public void addTodo(String name, String description, LocalDate targetDate) {
        todos.add(new Todo(todos.size()+1, name, description, targetDate, false));
    }
    
    public void updateTodo(Todo todo) {
        for (int i=0; i < todos.size(); i++) {
            Todo currentTodo = todos.get(i);
            if (currentTodo.getId() == todo.getId()) {
                System.out.println("Updating Todo Description: " + todo.getDescription());
                currentTodo.setDescription(todo.getDescription());
                currentTodo.setTargetDate(todo.getTargetDate());
            }
        }
    }
    
    public void deleteTodo(int id) {
        for (int i=0; i < todos.size(); i++) {
            Todo todo = todos.get(i);
            if (todo.getId() == id) {
                todos.remove(todo);
            }
        }
    }
    
}
