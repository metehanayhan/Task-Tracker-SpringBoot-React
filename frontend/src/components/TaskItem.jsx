const TaskItem = ({ task, onToggleComplete, onDeleteTask }) => {
  return (
    <div className="task-item">
      <input
        type="checkbox"
        checked={task.completed}
        onChange={() => onToggleComplete(task)}
      />
      <span className={task.completed ? "completed" : ""}>{task.title}</span>
      <button className="delete-btn" onClick={() => onDeleteTask(task.id)}>
        Sil
      </button>
    </div>
  );
};

export default TaskItem;
