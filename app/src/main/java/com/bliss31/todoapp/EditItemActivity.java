package com.bliss31.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText editText;
    int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemText = getIntent().getStringExtra("itemText");
        editText = (EditText) findViewById(R.id.etUpdateItem);
        editText.setText(itemText);
        editText.setSelection(itemText.length());
        itemPosition = getIntent().getIntExtra("itemPosition", 0);
    }
    public void onEditSubmit(View view) {
        Intent result = new Intent();
        String itemText = editText.getText().toString();
        result.putExtra("itemText", itemText);
        result.putExtra("itemPosition", itemPosition);
        setResult(RESULT_OK, result);
        finish();
    }

}
