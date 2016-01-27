package com.martinozgun.notes.data.dao;

import com.google.inject.ImplementedBy;

import com.martinozgun.notes.data.Note;
import com.martinozgun.notes.data.dao.impl.sqlite.NoteSQLiteDAO;

import java.util.List;

/**
 * Created by Martin Özgun.
 */
@ImplementedBy(NoteSQLiteDAO.class)
public interface NoteDAO {

    List<Note> fetchAll();


    void insert(Note note);


    void update(Note note);


    void delete(Note note);
}