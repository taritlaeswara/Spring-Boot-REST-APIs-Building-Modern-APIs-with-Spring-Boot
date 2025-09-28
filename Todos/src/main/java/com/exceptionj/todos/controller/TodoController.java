package com.exceptionj.todos.controller;

import com.exceptionj.todos.request.TodoRequest;
import com.exceptionj.todos.response.TodoResponse;
import com.exceptionj.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Todo", description = "Endpoints for managing todo items")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Create a new todo", description = "Creates a new todo item with the provided details")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody TodoRequest todoRequest) {
        return todoService.createTodo(todoRequest);
    }

    @Operation(summary = "Get all todos", description = "Retrieves all todo items for the authenticated user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getAllTodos() {
        return todoService.getAllTodos();
    }
    @Operation(summary = "Toggle todo completion", description = "Toggles the completion status of a specific todo item by its ID")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/toggle")
    public TodoResponse toggleTodoCompletion(@PathVariable @Min(1) Long id) {
        return todoService.toggleTodoCompletion(id);
    }

    @Operation(summary = "Delete a todo", description = "Deletes a specific todo item by its ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable @Min(1) Long id) {
        todoService.deleteTodo(id);
    }
}
