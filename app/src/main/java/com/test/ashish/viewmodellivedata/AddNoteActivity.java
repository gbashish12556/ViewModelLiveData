package com.test.ashish.viewmodellivedata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText descriptionEditText;
    NumberPicker priorityNumberPicker;

    String title;
    String description;
    String priority;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleEditText = findViewById(R.id.title);
        descriptionEditText = findViewById(R.id.description);
        priorityNumberPicker = findViewById(R.id.priority);

        priorityNumberPicker.setMinValue(1);
        priorityNumberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveNote(){

        title = titleEditText.getText().toString().trim();
        description = descriptionEditText.getText().toString().trim();
        priority = String.valueOf(priorityNumberPicker.getValue());

        if(title == "" || description == "" ){
            Toast.makeText(this, "Enter title and description",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("title",title);
        data.putExtra("description",description);
        data.putExtra("priority",priority);
        setResult(RESULT_OK, data);
        finish();
    }
}
