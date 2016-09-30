package com.bliss31.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import com.bliss31.todoapp.util.TodoDate;

public class EditItemActivity extends AppCompatActivity {
    EditText editText;
    int itemPosition;
    long dueDateInMillis = 0;

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
        setResult(RESULT_OK, result);
        finish();
    }

}
