package com.bliss31.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import com.bliss31.todoapp.adapters.TodosAdapter;
import com.bliss31.todoapp.models.TodoModel;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ArrayList<TodoModel> todoItems;
    TodosAdapter aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int EDIT_ITEM_REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                TodoModel todo = todoItems.get(position);
                editIntent.putExtra("itemText", todo.getTodoText());
                editIntent.putExtra("itemPosition", position);
                editIntent.putExtra("dueDateInMillis", todo.getDueDateInMillis());
                startActivityForResult(editIntent, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
            String itemText = result.getStringExtra("itemText");
            int itemPosition = result.getIntExtra("itemPosition", 0);
            TodoModel todo = new TodoModel();
            long dueDateInMillis = result.getLongExtra("dueDateInMillis", 0);
            todo.setTodoText(itemText);
            todo.setDueDateInMillis(dueDateInMillis);
            todoItems.set(itemPosition, todo);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }

   }
    public void populateArrayItems() {
        todoItems = new ArrayList<TodoModel>();
        readItems();
        aToDoAdapter = new TodosAdapter(this, todoItems);

    }
    private void readItems() {
        List<TodoModel> todoModelList = SQLite.select().
                from(TodoModel.class).queryList();

        for (TodoModel todo : todoModelList) {
            todoItems.add(todo);
        }
    }

    private void writeItems() {
        TodoModel.deleteAllTodos();
        for (TodoModel todo: todoItems) {
            todo.save();
        }
    }

    public void onAddItem(View view) {
        String todoText = etEditText.getText().toString();
        TodoModel todo = new TodoModel();
        todo.setTodoText(todoText);
        todo.save();
        aToDoAdapter.add(todo);
        etEditText.setText("");
        writeItems();
    }
}
