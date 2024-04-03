package com.freshworks.taskmanager;

import java.util.ArrayList;
import java.util.List;

public interface TodoService {
    List<TodoItem> getAllTodoItems();
    void addTodoItem(TodoItem todoItem);
    void updateTodoItem(int index, boolean completed);

    default List<TodoItem> getActiveTodoItems() {
        List<TodoItem> activeTodoItems = new ArrayList<>();
        for (TodoItem item : getAllTodoItems()) {
            if (!item.isCompleted()) {
                activeTodoItems.add(item);
            }
        }
        return activeTodoItems;
    }

    default List<TodoItem> getCompletedTodoItems() {
        List<TodoItem> completedTodoItems = new ArrayList<>();
        for (TodoItem item : getAllTodoItems()) {
            if (item.isCompleted()) {
                completedTodoItems.add(item);
            }
        }
        return completedTodoItems;
    }

}
