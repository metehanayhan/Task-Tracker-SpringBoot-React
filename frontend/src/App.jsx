import { useState, useEffect } from "react";
import "./App.css";
import Header from "./components/Header";
import TaskForm from "./components/TaskForm";
import TaskList from "./components/TaskList";
import {
  getAllTasks,
  createTask,
  updateTask,
  deleteTask,
} from "./services/taskService";

function App() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    fetchTasks();
  }, []);

  // GET
  const fetchTasks = async () => {
    const response = await getAllTasks();
    setTasks(response.data);
  };

  // POST
  const handleAddTask = async (taskTitle) => {
    const newTask = {
      title: taskTitle,
      completed: false,
    };
    const response = await createTask(newTask);
    setTasks([...tasks, response.data]);
  };

  // DELETE
  const handleDeleteTask = async (taskId) => {
    await deleteTask(taskId);
    const updatedTasks = tasks.filter((task) => task.id !== taskId);
    setTasks(updatedTasks);
  };

  // UPDATE
  const handleToggleComplete = async (task) => {
    const updatedTask = { ...task, completed: !task.completed };
    const response = await updateTask(task.id, updatedTask);
    const updatedTasks = tasks.map((t) =>
      t.id === task.id ? response.data : t
    );
    setTasks(updatedTasks);
  };

  return (
    <div className="app-container">
      <Header />

      <TaskForm onAddTask={handleAddTask} />

      <hr />

      <TaskList
        tasks={tasks}
        onToggleComplete={handleToggleComplete}
        onDeleteTask={handleDeleteTask}
      />
    </div>
  );
}

export default App;
