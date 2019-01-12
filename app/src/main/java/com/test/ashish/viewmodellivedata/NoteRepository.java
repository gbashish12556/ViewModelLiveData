package com.test.ashish.viewmodellivedata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNOtes;

    public NoteRepository(Application application){
        NoteDatabase  noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNOtes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new InserAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNOtes(){
        return allNOtes;
    }

    public static class InserAsyncTask extends AsyncTask<Note, Void,Void>{

        private NoteDao noteDao;

        public InserAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Note, Void,Void>{

        private NoteDao noteDao;

        public UpdateAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Note, Void,Void>{

        private NoteDao noteDao;

        public DeleteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void,Void>{

        private NoteDao noteDao;

        public DeleteAllAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
