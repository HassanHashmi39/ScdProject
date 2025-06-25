package taskmanager;

import java.time.LocalDate;

public class Task {
    private String title;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(String title, LocalDate dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public String getTitle() { return title; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isCompleted() { return isCompleted; }
    public void markAsCompleted() { this.isCompleted = true; }

    @Override
    public String toString() {
        return title + " - " + dueDate + " - " + (isCompleted ? "Completed" : "Pending");
    }
}
