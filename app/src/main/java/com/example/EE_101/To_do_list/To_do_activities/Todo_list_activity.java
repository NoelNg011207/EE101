package com.example.EE_101.To_do_list.To_do_activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.EE_101.Financial_Planner.Budget_Planner;
import com.example.EE_101.MainActivity;
import com.example.EE_101.Notes.NotesListActivity;
import com.example.EE_101.R;
import com.example.EE_101.Scholarship.SholarshipListActivity;
import com.example.EE_101.To_do_list.adapter.NotesAdapter;
import com.example.EE_101.To_do_list.database.NotesDatabase;
import com.example.EE_101.To_do_list.entities.Note;
import com.example.EE_101.To_do_list.listener.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class Todo_list_activity extends AppCompatActivity implements NotesListener {

    public static final int REQUEST_CODE_ADD_NOTES = 1;
    public static final int REQUEST_CODE_UPDATE_NOTES = 2;
    public static final int REQUEST_CODE_SHOW_NOTES = 3;

    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    private int noteClickedPosition= -1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(view -> {
            startActivityForResult(new Intent(getApplicationContext(), CreateNoteActivity.class),
                    REQUEST_CODE_ADD_NOTES
            );

        });

        ImageView BackToMainMenu = findViewById(R.id.imageBackNotes);
        BackToMainMenu.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
            );
        });

        ImageButton intentSL = findViewById(R.id.intentScholarship);
        intentSL.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), SholarshipListActivity.class)
            );
        });

        ImageButton intentnotes = findViewById(R.id.intentNotes);
        intentnotes.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), NotesListActivity.class)
            );
        });

        ImageButton intentHome = findViewById(R.id.homebtn);
        intentHome.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
            );
        });

        ImageButton intentFA = findViewById(R.id.intentFA);
        intentFA.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), Budget_Planner.class)
            );
        });

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, this);
        notesRecyclerView.setAdapter(notesAdapter);

        getNotes(REQUEST_CODE_SHOW_NOTES,false);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE_ADD_NOTES && resultCode == RESULT_OK){
            getNotes(REQUEST_CODE_ADD_NOTES, false);
        }else if (requestCode == REQUEST_CODE_UPDATE_NOTES && resultCode == RESULT_OK){
            if(data !=null){
                getNotes(REQUEST_CODE_UPDATE_NOTES,data.getBooleanExtra("isNoteDeleted",false));
            }
        }
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(),CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTES);
    }

    private void getNotes(final int requestCode, final boolean isNoteDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (requestCode == REQUEST_CODE_SHOW_NOTES) {
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                }else if (requestCode == REQUEST_CODE_ADD_NOTES) {
                    noteList.add(0,notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                    notesAdapter.notifyDataSetChanged();
                    notesRecyclerView.setAdapter(notesAdapter);

                } else if (requestCode == REQUEST_CODE_UPDATE_NOTES) {
                    noteList.remove(noteClickedPosition);
                    if (isNoteDeleted) {
                        notesAdapter.notifyItemRemoved(noteClickedPosition);
                        notesAdapter.notifyDataSetChanged();
                    } else {
                        notesAdapter.notifyItemChanged(noteClickedPosition);
                        notesAdapter.notifyDataSetChanged();
                        noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
                    }
                }
            }
        }
        new GetNotesTask().execute();
    }

}