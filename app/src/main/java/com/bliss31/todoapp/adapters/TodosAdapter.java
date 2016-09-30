package com.bliss31.todoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bliss31.todoapp.R;
import com.bliss31.todoapp.models.TodoModel;

import java.util.ArrayList;

/**
 * Created by jdemott on 9/29/16.
 */

public class TodosAdapter extends ArrayAdapter<TodoModel> {
    public TodosAdapter(Context context, ArrayList<TodoModel> todos) {
        super(context, 0, todos);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoModel todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView todoTextView = (TextView) convertView.findViewById(R.id.todoTextView);
        todoTextView.setText(todo.getTodoText());
        return convertView;
    }
}
