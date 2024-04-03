package com.freshworks.taskmanager;

import com.freshworks.taskmanager.TodoItem;
import com.freshworks.taskmanager.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(Model model) {
        List<TodoItem> todoItems = todoService.getAllTodoItems();
        List<TodoItem> activeTodos = todoService.getActiveTodoItems();
        List<TodoItem> completedTodos = todoService.getCompletedTodoItems();
        model.addAttribute("todoItems", todoItems);
        model.addAttribute("activeTodos",activeTodos);
        model.addAttribute("completedTodos",completedTodos);
        return "index"; // Return the name of the HTML template
    }
    @PostMapping("/addTodo")
    public String addTodoItem(TodoItem todoItem) {
        todoService.addTodoItem(todoItem);
        return "redirect:/";
    }

    @PostMapping("/updateTodo")
    public String updateTodoItem(int index, boolean completed) {
        todoService.updateTodoItem(index, completed);
        return "redirect:/";
    }

}
