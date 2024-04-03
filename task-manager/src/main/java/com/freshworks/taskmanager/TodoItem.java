package com.freshworks.taskmanager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItem {

    private String fromDateTime;
    private String toDateTime;
    private String description;
    private boolean completed;

    // Getters and setters
}
