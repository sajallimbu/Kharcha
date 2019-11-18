package com.example.kharcha;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddNoteDialog extends AppCompatDialogFragment {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;

    private AddDialogListener listener;
    private ImageView addNoteImage;
    private TextView addNoteDescription;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        addNoteImage = getActivity().findViewById(R.id.add_note_image);
        addNoteDescription = getActivity().findViewById(R.id.add_note_description);

        View view = inflater.inflate(R.layout.layout_add_note, null);
        builder.setView(view)
                .setTitle("Add Note")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextTitle.getText().toString();
                        String description = editTextDescription.getText().toString();
                        String priority = editTextPriority.getText().toString();
                        if (title.trim().isEmpty() || description.trim().isEmpty() || priority.trim().isEmpty()) {
                            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            addNoteImage.setVisibility(View.INVISIBLE);
                            addNoteDescription.setVisibility(View.INVISIBLE);
                            listener.applyNote(title, description, priority);
                        }
                    }
                });

        editTextTitle = view.findViewById(R.id.note_title);
        editTextDescription = view.findViewById(R.id.note_description);
        editTextPriority = view.findViewById(R.id.number_priority);
        return builder.create();
    }

    public interface AddDialogListener {
        void applyNote(String title, String description, String priority);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AddDialogListener");
        }
    }
}
