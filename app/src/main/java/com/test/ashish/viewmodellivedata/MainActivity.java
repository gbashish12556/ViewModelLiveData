package com.test.ashish.viewmodellivedata;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private List<Note> allNotes;
    public static final int ADD_NOTE_REQUEST = 5;
    public static final int EDIT_NOTE_REQUEST = 6;
    public static int LEAK_INT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allNotes = new ArrayList<>() ;
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });
        final NoteAdapter noteAdapter = new NoteAdapter();
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra("title",note.getTitle());
                intent.putExtra("description",note.getDescription());
                intent.putExtra("priority",note.getPriority());
                intent.putExtra("id",note.getId());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = (NoteViewModel) ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this,new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //Data Changed
                noteAdapter.submitList(notes);
            }
        });

       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
           @Override
           public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = noteAdapter.getNoteAt(position);
                noteViewModel.delete(note);
               Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
           }
       }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){

            String title =  data.getStringExtra("title");
            String description =  data.getStringExtra("description");
            String priority =  data.getStringExtra("priority");
            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);
            Toast.makeText(this,"Note saved",Toast.LENGTH_SHORT).show();

        } else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            String title =  data.getStringExtra("title");
            String description =  data.getStringExtra("description");
            String priority =  data.getStringExtra("priority");
            Note note = new Note(title,description,priority);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);
            Toast.makeText(this,"Note saved",Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this,"Note not saved",Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAll();
                Toast.makeText(this,"Note Deleted",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
