package com.bliss31.todoapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bliss31.todoapp.R;

import java.sql.Date;
import java.util.Calendar;

public class EditDueDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_due_date);

        setUpDatePicker();
        setOnSaveHandler();

    }

    private void setUpDatePicker() {
        long dueDateInMillis = getIntent().getLongExtra("dueDateInMillis", 0);
        DatePicker datePicker = (DatePicker) findViewById(R.id.dpDueDate);
        datePicker.setMinDate(System.currentTimeMillis());
        if (dueDateInMillis > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dueDateInMillis);
            datePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE)
            );
        }
    }

    protected void setOnSaveHandler() {
        Button saveButton = (Button) findViewById(R.id.btnDueDateDone);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) findViewById(R.id.dpDueDate);
                Calendar calendar = Calendar.getInstance();
                calendar.set(
                        datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        0,
                        0
                );
                long dueDateInMillis = calendar.getTimeInMillis();
                Intent result = new Intent();
                result.putExtra("dueDateInMillis", dueDateInMillis);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }
}
