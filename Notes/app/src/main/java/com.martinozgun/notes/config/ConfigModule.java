package com.martinozgun.notes.config;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.martinozgun.notes.data.source.sqlite.NotesDatabaseHelper;

/**
 * Created by Martin Özgun.
 */
public class ConfigModule extends AbstractModule {

    private final Application context;

    public ConfigModule(Application context) {
        this.context = context;
    }

    @Override
    protected void configure() {
        bind(SQLiteOpenHelper.class)
                .annotatedWith(Names.named("NotesDbHelper"))
                .toInstance(new NotesDatabaseHelper(context));
    }
}