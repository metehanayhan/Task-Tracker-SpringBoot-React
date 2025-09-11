import { useState } from "react";

const TaskForm = ({ onAddTask }) => {
  const [newTaskTitle, setNewTaskTitle] = useState("");

  const handleAddTask = (e) => {
    e.preventDefault();

    if (!newTaskTitle.trim()) return;

    onAddTask(newTaskTitle);
    setNewTaskTitle("");
  };

  return (
    <form className="add-task-form" onSubmit={handleAddTask}>
      <input
        type="text"
        placeholder="Yeni bir görev ekle..."
        value={newTaskTitle}
        onChange={(e) => setNewTaskTitle(e.target.value)}
      />
      <button type="submit">Ekle</button>
    </form>
  );
};

export default TaskForm;
