package com.henkbjork.todomvc.service;

import com.henkbjork.todomvc.model.Todo;
import com.henkbjork.todomvc.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todo> getAllTodos() {
        return (List<Todo>) todoRepository.findAll();
    }


    @Override
    public Todo save(Todo newTodo) {
        newTodo.setChecked(false);
        System.out.println(newTodo.toString());
        return todoRepository.save(newTodo);
    }

    @Override
    public Todo findById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()) {
            return todo.get();
        } else {
            return null;
        }
    }

    @Override
    public Todo update(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void setTodoStatus(Long id) {
        Todo todo = todoRepository.findById(id).get();
        if (todo.isChecked()) {
            todo.setChecked(false);
        } else {
            todo.setChecked(true);
        }
    }
}
