package com.example.kharcha.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha.AddNoteDialog;
import com.example.kharcha.R;
import com.example.kharcha.room.Note;

import java.util.List;

public class NotesFragment extends Fragment {

    private NotesViewModel notesViewModel;
    private ImageView addNoteImage;
    private TextView addNoteDescription;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addNoteImage = getView().findViewById(R.id.add_note_image);
        addNoteDescription = getView().findViewById(R.id.add_note_description);

        RecyclerView noteRecyclerView = getView().findViewById(R.id.note_recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        noteRecyclerView.setHasFixedSize(true);

        final NotesAdapter adapter = new NotesAdapter();
        noteRecyclerView.setAdapter(adapter);

        notesViewModel = ViewModelProviders.of(getActivity()).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (!notes.isEmpty()) {
                    addNoteDescription.setVisibility(View.INVISIBLE);
                    addNoteImage.setVisibility(View.INVISIBLE);
                } else {
                    addNoteImage.setVisibility(View.VISIBLE);
                    addNoteDescription.setVisibility(View.VISIBLE);
                }
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notesViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(noteRecyclerView);
    }

}
