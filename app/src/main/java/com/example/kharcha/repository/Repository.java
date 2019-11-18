package com.example.kharcha.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.kharcha.room.Expense;
import com.example.kharcha.room.ExpenseDao;
import com.example.kharcha.room.ExpenseDatabase;
import com.example.kharcha.room.Note;
import com.example.kharcha.room.NoteDao;
import com.example.kharcha.room.NoteDatabase;

import java.util.List;

public class Repository {

    private ExpenseDao expenseDao;
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Expense>> allExpense;
    private LiveData total;

    public Repository(Application application) {
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        expenseDao = database.expenseDao();
        allExpense = expenseDao.getAllExpense();
        total = expenseDao.getTotal();
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }


    public void insert(Expense expense) {
        new InsertExpenseAsyncTask(expenseDao).execute(expense);
    }

    public void deleteAllExpense() {
        new DeleteAllExpenseAsyncTask(expenseDao).execute();
    }

    public LiveData<List<Expense>> getAllExpense() {
        return allExpense;
    }

    public LiveData<Integer> getTotal() {
        return total;
    }


    private static class InsertExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {

        private ExpenseDao expenseDao;

        private InsertExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.insert(expenses[0]);
            return null;
        }
    }

    private static class DeleteAllExpenseAsyncTask extends AsyncTask<Void, Void, Void> {

        private ExpenseDao expenseDao;

        private DeleteAllExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.deleteAllExpense();
            return null;
        }
    }

    //repository api's that communicates with the viewmodel
    public void insertNote(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    //create async task for each method
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
