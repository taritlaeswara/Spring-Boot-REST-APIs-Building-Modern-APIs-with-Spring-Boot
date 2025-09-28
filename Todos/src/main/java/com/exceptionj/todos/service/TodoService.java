package com.exceptionj.todos.service;

import com.exceptionj.todos.request.TodoRequest;
import com.exceptionj.todos.response.TodoResponse;

import java.util.List;

public interface TodoService {

    TodoResponse createTodo(TodoRequest request);

    List<TodoResponse> getAllTodos();

    TodoResponse toggleTodoCompletion(Long id);

    void deleteTodo(Long id);

}
