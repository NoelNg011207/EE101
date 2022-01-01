package com.example.EE_101.To_do_list.listener;

import com.example.EE_101.To_do_list.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
