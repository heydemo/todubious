package com.bliss31.todoapp.models;

import android.util.Log;
import android.widget.Toast;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

/**
 * Created by jdemott on 9/28/16.
 */

@Database(name = TodoDatabase.NAME, version = TodoDatabase.VERSION)
public class TodoDatabase {
    public static final String NAME = "TodoDatabase";
    public static final int VERSION = 5;

    @Migration(version = 4, database = TodoDatabase.class)
    public static class Migration4 extends BaseMigration {
        @Override
        public void migrate(DatabaseWrapper databaseWrapper) {
            databaseWrapper.execSQL("ALTER TABLE TodoModel ADD COLUMN dueDateInMillis INTEGER NOT NULL DEFAULT 0");
        }
    }
    @Migration(version = 5, database = TodoDatabase.class)
    public static class Migration5 extends BaseMigration {
        @Override
        public void migrate(DatabaseWrapper databaseWrapper) {
            databaseWrapper.execSQL("ALTER TABLE TodoModel ADD COLUMN priority INTEGER NOT NULL DEFAULT 0");
        }
    }
}
