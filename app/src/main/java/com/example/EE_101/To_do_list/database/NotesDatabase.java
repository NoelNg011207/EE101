package com.example.EE_101.To_do_list.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.EE_101.To_do_list.dao.NoteDao;
import com.example.EE_101.To_do_list.entities.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract  class NotesDatabase extends RoomDatabase{

    private static NotesDatabase notesDatabase;

    public static synchronized NotesDatabase getDatabase(Context context){
        if (notesDatabase == null){
            notesDatabase = Room.databaseBuilder(
                    context,
                    NotesDatabase.class,
                    "notes_db"
            ).build();
        }
        return notesDatabase;
    }

    public abstract NoteDao noteDao();
}
