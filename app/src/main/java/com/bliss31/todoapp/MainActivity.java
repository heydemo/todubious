package com.bliss31.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.bliss31.todoapp.models.TodoModel;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
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
                Intent edit_intent = new Intent(MainActivity.this, EditItemActivity.class);
                edit_intent.putExtra("item_text", todoItems.get(position));
                edit_intent.putExtra("item_position", position);
                startActivityForResult(edit_intent, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
            String item_text = result.getStringExtra("item_text");
            int item_position = result.getIntExtra("item_position", 0);
            todoItems.set(item_position, item_text);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }

   }
    public void populateArrayItems() {
        todoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }
    private void readItems() {
        List<TodoModel> todoModelList = SQLite.select().
                from(TodoModel.class).queryList();

        for (TodoModel todo : todoModelList) {
            todoItems.add(todo.getTodoText());
        }
    }

    private void writeItems() {
        TodoModel.deleteAllTodos();
        for (String todoText : todoItems) {
            TodoModel todo = new TodoModel();
            todo.setTodoText(todoText);
            todo.save();
        }
    }

    public void onAddItem(View view) {
        String todo_text = etEditText.getText().toString();
        TodoModel todo = new TodoModel();
        todo.setTodoText(todo_text);
        todo.save();
        Toast.makeText(this, "Created TODO", Toast.LENGTH_SHORT).show();
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}
