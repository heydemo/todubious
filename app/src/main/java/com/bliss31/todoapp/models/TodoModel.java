package com.bliss31.todoapp.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

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

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }
    public String getTodoText() {
        return this.todoText;
    }
    public static void deleteAllTodos() {
        SQLite.delete().from(TodoModel.class).execute();
    }
}
