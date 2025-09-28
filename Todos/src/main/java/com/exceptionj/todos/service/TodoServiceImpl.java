package com.exceptionj.todos.service;

import com.exceptionj.todos.entity.Todo;
import com.exceptionj.todos.entity.User;
import com.exceptionj.todos.repository.TodoRepository;
import com.exceptionj.todos.request.TodoRequest;
import com.exceptionj.todos.response.TodoResponse;
import com.exceptionj.todos.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoServiceImpl  implements TodoService{
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final TodoRepository todoRepository;

    public TodoServiceImpl(FindAuthenticatedUser findAuthenticatedUser, TodoRepository todoRepository) {
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.todoRepository = todoRepository;
    }


    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest request) {
        User authenticatedUser = findAuthenticatedUser.getAuthenticatedUser();
        Todo todo = new Todo(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                false,
                authenticatedUser
        );
        Todo savedTodo = todoRepository.save(todo);

        TodoResponse todoResponse = new TodoResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getDescription(),
                savedTodo.getPriority(),
                savedTodo.isComplete()
        );

        return todoResponse;
    }
}
