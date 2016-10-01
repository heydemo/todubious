package com.bliss31.todoapp;

import android.content.Intent;
import android.os.Handler;
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

import pl.droidsonroids.gif.GifImageView;


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
                final GifImageView explosion = (GifImageView) view.findViewById(R.id.ivExplosion);
                explosion.setImageResource(R.drawable.explosion);
                final Handler handler = new Handler();
                handler.postDelayed(new RunnableWithPosition(position) {
                    @Override
                    public void run() {
                        todoItems.remove(this.position);
                        aToDoAdapter.notifyDataSetChanged();
                        explosion.setImageResource(0);
                        writeItems();
                    }
                }, 600);

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
                editIntent.putExtra("priority", todo.getPriority());
                startActivityForResult(editIntent, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {
            String itemText = result.getStringExtra("itemText");
            int itemPosition = result.getIntExtra("itemPosition", 0);
            long dueDateInMillis = result.getLongExtra("dueDateInMillis", 0);
            int priority = result.getIntExtra("priority", 0);

            TodoModel todo = new TodoModel();
            todo.setTodoText(itemText);
            todo.setDueDateInMillis(dueDateInMillis);
            todo.setPriority(priority);

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


class RunnableWithPosition implements Runnable {
    protected int position;
    public RunnableWithPosition(int position) {
        this.position = position;
    }

    @Override
    public void run() {
    }
}
