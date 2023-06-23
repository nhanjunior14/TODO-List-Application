package project.group.todo.Object;

import android.os.Parcel;

import java.io.Serializable;

import io.realm.RealmObject;

public class Todo extends RealmObject implements Serializable {
    private int id;
    private String title;
    private String content;
    private String date;

    public Todo(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Todo(int id, String title, String content, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    //constructor
    public Todo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Todo() {}

    //getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    //setter
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //Parcelable
    protected Todo(Parcel in) {
        title = in.readString();
        content = in.readString();
    }

    public void updateFromRealm(Todo addTodo) {
        this.setId(addTodo.getId());
        this.setTitle(addTodo.getTitle());
        this.setContent(addTodo.getContent());
        this.setDate(addTodo.getDate());
    }
}
