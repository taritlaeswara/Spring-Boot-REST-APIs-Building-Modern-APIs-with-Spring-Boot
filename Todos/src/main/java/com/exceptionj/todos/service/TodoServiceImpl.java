package com.exceptionj.todos.service;

import com.exceptionj.todos.entity.Todo;
import com.exceptionj.todos.entity.User;
import com.exceptionj.todos.repository.TodoRepository;
import com.exceptionj.todos.request.TodoRequest;
import com.exceptionj.todos.response.TodoResponse;
import com.exceptionj.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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

        TodoResponse todoResponse = convertToResponse(savedTodo);

        return todoResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
        User authenticatedUser = findAuthenticatedUser.getAuthenticatedUser();
        List<Todo> todos = todoRepository.findByOwner(authenticatedUser);
        return todos.stream().map(this::convertToResponse).toList();
    }

    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(Long id) {
        User authenticatedUser = findAuthenticatedUser.getAuthenticatedUser();

        Optional<Todo> todo = todoRepository.findByIdAndOwner(id,authenticatedUser);
        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Todo not found");
        }
        todo.get().setComplete(!todo.get().isComplete());
        return convertToResponse(todoRepository.save(todo.get()));
    }

    @Override
    @Transactional
    public void deleteTodo(Long id) {
        User authenticatedUser = findAuthenticatedUser.getAuthenticatedUser();

        Optional<Todo> todo = todoRepository.findByIdAndOwner(id,authenticatedUser);
        if(todo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoRepository.delete(todo.get());
    }

    private TodoResponse convertToResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }
}
