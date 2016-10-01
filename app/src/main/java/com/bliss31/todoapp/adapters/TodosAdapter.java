package com.bliss31.todoapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bliss31.todoapp.R;
import com.bliss31.todoapp.models.TodoModel;
import com.bliss31.todoapp.util.TodoDate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by jdemott on 9/29/16.
 */

public class TodosAdapter extends ArrayAdapter<TodoModel> {
    Context adapterContext;
    public TodosAdapter(Context context, ArrayList<TodoModel> todos) {
        super(context, 0, todos);
        adapterContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoModel todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView todoTextView = (TextView) convertView.findViewById(R.id.todoTextView);
        todoTextView.setText(todo.getTodoText());
        setDueDate(convertView, todo);
        setPriorityText(convertView, todo);
        return convertView;
    }
    protected void setPriorityText(View convertView, TodoModel todo) {
        TextView priorityTextView = (TextView) convertView.findViewById(R.id.tvPriority);
        String[] priorities = adapterContext.getResources().getStringArray(R.array.priority_options);
        int position = todo.getPriority();
        String priorityString = priorities[position];
        priorityTextView.setText(priorityString);

    }
    protected void setDueDate(View convertView, TodoModel todo) {
        String dueString = "";
        long dueDateInMillis = todo.getDueDateInMillis();
        TextView dueDateTextView = (TextView) convertView.findViewById(R.id.tvDueDateList);
        if (dueDateInMillis > 0) {
            String dueDate = TodoDate.longToString(dueDateInMillis);
            String dueLabelString = adapterContext.getString(R.string.due);
            dueString = dueLabelString + " " + dueDate;
        }
        dueDateTextView.setText(dueString);
    }
}
