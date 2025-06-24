package TaskSetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TaskSetter extends JFrame {

    private static final long serialVersionUID = 1L;

    private final taskmanager taskManager = new taskmanager();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> taskList = new JList<>(listModel);

    public TaskSetter() {
        setTitle("📋 Task Setter");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // === Fonts ===
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.setForeground(new Color(255, 255, 255));
        deleteButton.setBackground(new Color(178, 34, 34));
        JButton completeButton = new JButton("Mark Completed");
        completeButton.setBackground(new Color(154, 205, 50));

        // === Input Panel with GridBagLayout ===
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(238, 232, 170));
        inputPanel.setLayout(new GridBagLayout());

        // Row 0 - Task Title
        JLabel titleLabel = new JLabel("Task Title:");
        titleLabel.setFont(labelFont);

        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.insets = new Insets(5, 5, 5, 5);
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        inputPanel.add(titleLabel, gbcTitle);
        
                // === Input Fields ===
                JTextField titleField = new JTextField();
                titleField.setPreferredSize(new Dimension(200, 25));
                
                        GridBagConstraints gbcTitleField = new GridBagConstraints();
                        gbcTitleField.insets = new Insets(5, 5, 5, 5);
                        gbcTitleField.fill = GridBagConstraints.HORIZONTAL;
                        gbcTitleField.gridx = 0;
                        gbcTitleField.gridy = 1;
                        inputPanel.add(titleField, gbcTitleField);
                        String title = titleField.getText().trim();
                        titleField.setText("");

        // Row 1 - Due Date
        JLabel dateLabel = new JLabel("Due Date (yyyy-mm-dd):");
        dateLabel.setFont(labelFont);

        GridBagConstraints gbcDateLabel = new GridBagConstraints();
        gbcDateLabel.insets = new Insets(5, 5, 5, 5);
        gbcDateLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcDateLabel.gridx = 0;
        gbcDateLabel.gridy = 2;
        inputPanel.add(dateLabel, gbcDateLabel);
        JTextField dueDateField = new JTextField(); // Format: yyyy-mm-dd
        dueDateField.setPreferredSize(new Dimension(200, 25));
        
                GridBagConstraints gbcDueDateField = new GridBagConstraints();
                gbcDueDateField.insets = new Insets(5, 5, 5, 5);
                gbcDueDateField.fill = GridBagConstraints.HORIZONTAL;
                gbcDueDateField.gridx = 0;
                gbcDueDateField.gridy = 3;
                inputPanel.add(dueDateField, gbcDueDateField);
                String dateText = dueDateField.getText().trim();
                dueDateField.setText("");

        // === Task List ===
        taskList.setVisibleRowCount(10);
        taskList.setFixedCellHeight(30);
        taskList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane listScroll = new JScrollPane(taskList);
        listScroll.setPreferredSize(new Dimension(550, 250));

        // === Button Panel ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(238, 232, 170));
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        // === Main Layout ===
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        
                // === Buttons ===
                JButton addButton = new JButton("Add Task");
                
                        // Trigger Add Button on ENTER key press in title or dueDate field
                        titleField.addActionListener(e -> addButton.doClick());
                        dueDateField.addActionListener(e -> addButton.doClick());
                        
                                // Row 2 - Add Button
                                GridBagConstraints gbcAddButton = new GridBagConstraints();
                                gbcAddButton.insets = new Insets(5, 5, 5, 5);
                                gbcAddButton.gridx = 0;
                                gbcAddButton.gridy = 4;
                                gbcAddButton.anchor = GridBagConstraints.WEST;
                                inputPanel.add(addButton, gbcAddButton);
                                
                                        // === Event Listeners ===
                                
                                        // Add Task
                                        addButton.addActionListener(e -> {
                                            if (title.isEmpty() || dateText.isEmpty()) {
                                                JOptionPane.showMessageDialog(this, "Both fields are required.");
                                                return;
                                            }
                                
                                            try {
                                                LocalDate dueDate = LocalDate.parse(dateText);
                                                task task = new task(title, dueDate);
                                                taskManager.addTask(task);
                                                updateTaskList();
                                            } catch (DateTimeParseException ex) {
                                                JOptionPane.showMessageDialog(this, "Please enter a valid date (yyyy-mm-dd).");
                                            }
                                        });
        JButton sortButton = new JButton("Sort by Date");
        GridBagConstraints gbc_sortButton = new GridBagConstraints();
        gbc_sortButton.insets = new Insets(0, 0, 5, 0);
        gbc_sortButton.gridx = 2;
        gbc_sortButton.gridy = 4;
        inputPanel.add(sortButton, gbc_sortButton);
        
                // Sort
                sortButton.addActionListener(e -> {
                    taskManager.sortTasksByDueDate();
                    updateTaskList();
                });
        getContentPane().add(listScroll, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Delete Task
        deleteButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskManager.deleteTask(index);
                updateTaskList();
            } else {
                JOptionPane.showMessageDialog(this, "Select a task to delete.");
            }
        });

        // Mark Completed
        completeButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskManager.markTaskCompleted(index);
                updateTaskList();
            } else {
                JOptionPane.showMessageDialog(this, "Select a task to mark as completed.");
            }
        });

        // Exit Confirm
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?",
                        "Exit", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });

        setVisible(true);
    }

    private void updateTaskList() {
        listModel.clear();
        for (task task : taskManager.getAllTasks()) {
            listModel.addElement(task.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskSetter::new);
    }
}
