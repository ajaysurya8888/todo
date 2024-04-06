package com.freshworks.taskmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TodoServiceImpl implements TodoService {

    @Value("${csv.file.path}")
    private String csvFilePath;
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");



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
            Date toDateParse = inputFormat.parse(todoItem.getToDateTime());
            Date fromDateParse = inputFormat.parse(todoItem.getFromDateTime());
            writer.write(outputFormat.format(fromDateParse) + "," +
                    outputFormat.format(toDateParse) + "," +
                    todoItem.getDescription() + "," +
                    todoItem.isCompleted() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
