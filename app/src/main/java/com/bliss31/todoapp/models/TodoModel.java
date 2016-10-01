package com.bliss31.todoapp.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

/**
 * Created by jdemott on 9/28/16.
 */

@Table(database = TodoDatabase.class)
public class TodoModel extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int _id;

    @Column
    String todoText;

    @Column
    long dueDateInMillis;

    @Column
    int priority = 0;


    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }
    public String getTodoText() {
        return this.todoText;
    }
    public void setDueDateInMillis(long dueDateInMillis) {
        this.dueDateInMillis = dueDateInMillis;
    }
    public long getDueDateInMillis() {
        return this.dueDateInMillis;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return this.priority;
    }
    public static void deleteAllTodos() {
        SQLite.delete().from(TodoModel.class).execute();
    }
}
