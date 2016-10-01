package com.bliss31.todoapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bliss31.todoapp.R;
import com.bliss31.todoapp.util.TodoDate;

public class EditItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editText;
    int itemPosition;
    long dueDateInMillis = 0;
    int priority = 0;

    private final int EDIT_DUE_DATE_REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemText = getIntent().getStringExtra("itemText");
        editText = (EditText) findViewById(R.id.etUpdateItem);
        editText.setText(itemText);
        editText.setSelection(itemText.length());
        itemPosition = getIntent().getIntExtra("itemPosition", 0);
        setUpDueDateLink();
        setUpPrioritySpinner();
    }
    public void setUpDueDateLink() {
        TextView addDueDateLink = (TextView) findViewById(R.id.tvDueDate);
        if (dueDateInMillis == 0) {
          dueDateInMillis = getIntent().getLongExtra("dueDateInMillis", 0);
        }
        String dueDateString = getString(R.string.add_due_date);
        if (dueDateInMillis > 0) {
            dueDateString = getString(R.string.due) + " " + TodoDate.longToString(dueDateInMillis);
        }
        addDueDateLink.setText(dueDateString);


        addDueDateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditItemActivity.this, EditDueDateActivity.class);
                intent.putExtra("dueDateInMillis", dueDateInMillis);
                startActivityForResult(intent, EDIT_DUE_DATE_REQUEST_CODE);
            }
        });
    }
    public void setUpPrioritySpinner() {
        priority = getIntent().getIntExtra("priority", 0);
        Spinner dropdown = (Spinner)findViewById(R.id.spPriority);
        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
        dropdown.setSelection(priority);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        priority = pos;
    }
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK && requestCode == EDIT_DUE_DATE_REQUEST_CODE) {
            dueDateInMillis = result.getLongExtra("dueDateInMillis", 0);
            setUpDueDateLink();
            emphasizeDueDateChange();
        }
    }

    protected void emphasizeDueDateChange() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.text_emphasis);
        animation.reset();
        TextView dueDateTextView = (TextView) findViewById(R.id.tvDueDate);
        dueDateTextView.clearAnimation();
        dueDateTextView.startAnimation(animation);
    }

    public void onEditSubmit(View view) {
        Intent result = new Intent();
        String itemText = editText.getText().toString();
        result.putExtra("itemText", itemText);
        result.putExtra("itemPosition", itemPosition);
        result.putExtra("dueDateInMillis", dueDateInMillis);
        result.putExtra("priority", priority);
        setResult(RESULT_OK, result);
        finish();
    }

}
