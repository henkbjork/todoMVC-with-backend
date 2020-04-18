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
    private boolean toggle = false;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String todoList(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        int size = unChecked();
        model.addAttribute("unCheckedTodos", size);
        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "TodoMVC");
        return "view/todos";
    }

    private int unChecked() {
        List<Todo> todos = todoService.getAllTodos();
        List<Todo> unCheckedTodos = new ArrayList<>();
        for(Todo todo : todos) {
            if(!todo.isChecked()) {
                unCheckedTodos.add(todo);
            }
        }
       return unCheckedTodos.size();
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Todo todo, Model model) {
        if(todo.getTodoText() != "") {
            Todo newTodo = todoService.save(todo);
            if(newTodo != null) {
                return "redirect:/";
            } else {
                model.addAttribute("todo", todo);
                return "view/todos";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/deleteTodo/{id}")
    public String delete(@PathVariable Long id) {
        todoService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/updateTodo/{id}")
    public String update(@PathVariable Long id, Model model) {
        Todo todo = todoService.update(id);
        model.addAttribute("todo", todo);
        model.addAttribute("title", "Update Todo");
        return "view/todos";
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
        int size;
        if(!toggle) {
            for(Todo todo : todos) {
                todoService.setTodoChecked(todo.getId());
            }
            size = unChecked();
            toggle = true;
        } else {
            for(Todo todo : todos) {
                todoService.setTodoUnchecked(todo.getId());
            }
            size = unChecked();
            toggle = false;
        }
        model.addAttribute("unCheckedTodos", size);
        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "TodoMVC");
        return "view/todos";
    }

    @GetMapping("/active")
    public String getActiveTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        List<Todo> activeTodos = new ArrayList<>();
        int size = unChecked();
        for(Todo todo : todos) {
            if(!todo.isChecked()) {
                activeTodos.add(todo);
            }
        }
        model.addAttribute("unCheckedTodos", size);
        model.addAttribute("todos", activeTodos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "Active Todos");
        return "view/todos";
    }

    @GetMapping("/completed")
    public String getCompletedTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        List<Todo> completedTodos = new ArrayList<>();
        int size = unChecked();
        for(Todo todo : todos) {
            if(todo.isChecked()) {
                completedTodos.add(todo);
            }
        }
        model.addAttribute("unCheckedTodos", size);
        model.addAttribute("todos", completedTodos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "Completed Todos");
        return "view/todos";
    }

    @PostMapping("/clearCompleted")
    public String clearCompletedTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        for(Todo todo : todos) {
            if(todo.isChecked()) {
                todoService.delete(todo.getId());
            }
        }
        model.addAttribute("todos", todos);
        model.addAttribute("todo", new Todo());
        model.addAttribute("title", "TodoMVC");
        return "redirect:/";
    }
}
