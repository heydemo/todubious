package com.bliss31.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText editText;
    int item_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editText = (EditText) findViewById(R.id.etUpdateItem);
        editText.setText(getIntent().getStringExtra("item_text"));
        item_position = getIntent().getIntExtra("item_position", 0);
    }
    public void onEditSubmit(View view) {
        Intent result = new Intent();
        String item_text = editText.getText().toString();
        result.putExtra("item_text", item_text);
        result.putExtra("item_position", item_position);
        setResult(RESULT_OK, result);
        finish();
    }

}
