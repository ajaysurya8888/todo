package com.freshworks.taskmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Value("${csv.file.path}")
    private String csvFilePath;




    @Override
    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                TodoItem todoItem = new TodoItem();
                todoItem.setFromDateTime(parts[0]);
                todoItem.setToDateTime(parts[1]);
                todoItem.setDescription(parts[2]);
                todoItem.setCompleted(Boolean.parseBoolean(parts[3]));
                todoItems.add(todoItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todoItems;
    }

    @Override
    public void addTodoItem(TodoItem todoItem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            writer.write(todoItem.getFromDateTime() + "," +
                    todoItem.getToDateTime() + "," +
                    todoItem.getDescription() + "," +
                    todoItem.isCompleted() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTodoItem(int index, boolean completed) {
        List<TodoItem> todoItems = getAllTodoItems();
        if (index >= 0 && index < todoItems.size()) {
            todoItems.get(index).setCompleted(completed);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
                for (TodoItem todoItem : todoItems) {
                    writer.write(todoItem.getFromDateTime() + "," +
                            todoItem.getToDateTime() + "," +
                            todoItem.getDescription() + "," +
                            todoItem.isCompleted() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
