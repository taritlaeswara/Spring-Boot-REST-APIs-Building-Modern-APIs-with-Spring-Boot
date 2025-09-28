package com.exceptionj.todos.service;

import com.exceptionj.todos.request.TodoRequest;
import com.exceptionj.todos.response.TodoResponse;

public interface TodoService {

    TodoResponse createTodo(TodoRequest request);
}
