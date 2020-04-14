package com.henkbjork.todomvc.service;

import com.henkbjork.todomvc.model.Todo;
import java.util.List;

public interface TodoService {

    List<Todo> getAllTodos();

    Todo save(Todo newTodo);

    Todo findById(Long id);

    Todo update(Long id);

    void delete(Long id);

    void setTodoStatus(Long id);

    void setTodoChecked(Long id);

    void setTodoUnchecked(Long id);
}
