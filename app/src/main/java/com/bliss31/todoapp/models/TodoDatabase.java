package com.bliss31.todoapp.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by jdemott on 9/28/16.
 */

@Database(name = TodoDatabase.NAME, version = TodoDatabase.VERSION)
public class TodoDatabase {
    public static final String NAME = "TodoDatabase";
    public static final int VERSION = 1;
}
