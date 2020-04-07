package com.henkbjork.todomvc.controller;

import com.henkbjork.todomvc.model.Todo;
import com.henkbjork.todomvc.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;


@Controller
public class TodoController {


    private TodoService todoService;
    private boolean toggle;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
        this.toggle = false;
    }

    @GetMapping("/")
    public String todoList(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "TodoMVC");
        return "view/todos";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Todo todo, Model model) {
        Todo newTodo = todoService.save(todo);
        if(newTodo != null) {
            return "redirect:/";
        } else {
            model.addAttribute("todo", todo);
            return "view/todos";
        }
    }

    @GetMapping("/deleteTodo/{id}")
    public String delete(@PathVariable Long id) {
        todoService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/getTodo/{id}")
    public String setCheckStatus(@PathVariable Long id) {
        todoService.setTodoStatus(id);
        return "redirect:/";
    }

    @GetMapping("/all")
    public String getAllTodos() {
        return "redirect:/";
    }

    @GetMapping("/toggleAll")
    public String toggleAllTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        if(!toggle) {
            for (int i = 0; i < todos.size(); i++) {
                //todoService.setTodoStatus(todos.get(i).getId());
                todoService.setTodoChecked(todos.get(i).getId());
            }
            toggle = true;
        } else {
            for (int i = 0; i < todos.size(); i++) {
                //todoService.setTodoStatus(todos.get(i).getId());
                todoService.setTodoUnchecked(todos.get(i).getId());
            }
            toggle = false;
        }
        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "TodoMVC");
        return "view/todos";
    }

    @GetMapping("/active")
    public String getActiveTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        List<Todo> activeTodos = new ArrayList<>();
        for(int i=0; i<todos.size(); i++) {
            if(!todos.get(i).isChecked()) {
                activeTodos.add(todos.get(i));
            }
        }
        model.addAttribute("todos", activeTodos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "Active Todos");
        return "view/todos";
    }

    @GetMapping("/completed")
    public String getCompletedTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        List<Todo> completedTodos = new ArrayList<>();
        for(int i=0; i<todos.size(); i++) {
            if(todos.get(i).isChecked()) {
                completedTodos.add(todos.get(i));
            }
        }
        model.addAttribute("todos", completedTodos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "Completed Todos");
        return "view/todos";
    }

    @PostMapping("/clearCompleted")
    public String clearCompletedTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        for(int i=0; i<todos.size(); i++) {
            if(todos.get(i).isChecked()) {
                todoService.delete(todos.get(i).getId());
            }
        }
        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "TodoMVC");
        return "redirect:/";
    }
}
