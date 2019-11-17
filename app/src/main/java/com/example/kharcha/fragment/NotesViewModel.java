package com.example.kharcha.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kharcha.repository.Repository;
import com.example.kharcha.room.Note;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Note>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insertNote(note);
    }

    public void update(Note note) {
        repository.updateNote(note);
    }

    public void delete(Note note) {
        repository.deleteNote(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
