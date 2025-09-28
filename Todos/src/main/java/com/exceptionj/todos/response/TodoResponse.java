package com.exceptionj.todos.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoResponse {
    private long id;
    private String title;
    private String description;
    private int priority;
    private boolean complete;
}
