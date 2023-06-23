package project.group.todo.RealtimeDatabase;

import java.util.List;

import io.realm.Realm;

import project.group.todo.Object.Todo;

public class RealtimeDb {
    private Realm realtime;
    private static RealtimeDb realtimeDb;

    private RealtimeDb() {
        realtime = Realm.getDefaultInstance();
    }

    public static RealtimeDb getInstance() {
        if(realtimeDb == null) {
            realtimeDb = new RealtimeDb();
        }
        return realtimeDb;
    }

    public List<Todo> getAll() {
        return realtime.copyFromRealm(realtime.where(Todo.class).findAll());
    }

    private Todo getIdOfTodo(int todoId) {
        return realtime.where(Todo.class).equalTo("id", todoId).findFirst();
    }

    public void addTodoNew(Todo todo) {
        List<Todo> todoList = getAll();

        if(todoList.isEmpty() || todoList == null) {
            todo.setId(0);
        } else {
            todo.setId(1 + todoList.get(todoList.size() - 1).getId());
        }

        realtime.beginTransaction();
        Todo newContent = realtime.createObject(Todo.class);
        newContent.updateFromRealm(todo);
        realtime.copyFromRealm(newContent);
        realtime.commitTransaction();
    }

    public void deleteTodo(int todoId) {
        Todo todo = getIdOfTodo(todoId);

        realtime.beginTransaction();
        todo.deleteFromRealm();
        realtime.commitTransaction();
    }

    public void updateTodo(int todoId, Todo newContent) {
        Todo todoOld = getIdOfTodo(todoId);

        realtime.beginTransaction();
        todoOld.updateFromRealm(newContent);
        realtime.commitTransaction();
    }
}
