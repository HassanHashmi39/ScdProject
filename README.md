# Task Manager Application

A simple Java GUI-based Task Manager application for managing daily tasks efficiently. Built using Java Swing with a focus on modular design and collaborative development.


 Features
* Add new tasks with a title and due date.
* View all tasks (completed and pending).
* Mark tasks as completed.
* Delete tasks.
* Sort tasks by due date.
* Input validation with error handling.
* Replay option to continue or exit.


Technologies Used
* Java Swing (GUI)
* Java Collections (ArrayList)
* Java Exception Handling


Project Structure
TaskManager/
├── TaskManager.java  # Main GUI and functionality
├── Task.java         # Task class (model)
└── README.md         # Project documentation

Design Patterns Applied
* Model-View-Controller (MVC): Partially implemented by separating GUI and data logic.
* Recommended Future Patterns:
  * Singleton for session consistency.
  * Observer for real-time updates.


GitHub Workflow
* Feature branches were created for each phase (GUI, functionality, refactoring).
* Pull requests were reviewed by team members before merging.
* Code smells were identified and refactored based on review feedback.


How to Run
1. Clone the repository:

```bash
git clone <repo-url>
```

2. Open the project in Eclipse or any Java IDE.

3. Run `TaskManager.java` to start the application.


Test Plan Summary
* All key functionalities were tested, including adding, deleting, sorting, and validating inputs.
* Manual testing covered both success and failure scenarios.



Future Improvements
* Implement full MVC structure.
* Add notification/reminder features.
* Add persistent storage (file or database).
* Implement Observer pattern for real-time updates.
