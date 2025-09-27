package com.exceptionj.todos.globalexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponses {
    private int status;
    private String message;
    private Long timestamp;

}
