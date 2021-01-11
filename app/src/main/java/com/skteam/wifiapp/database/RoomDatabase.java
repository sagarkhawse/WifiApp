/*
 * Copyright (c)
 *  Ishant Sharma
 * Android Developer
 * ishant.sharma1947@gmail.com
 */

package com.skteam.wifiapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.skteam.wifiapp.database.entities.UserEntity;


@Database(entities = {UserEntity.class}, version = 0, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static RoomDatabase instance;
    private static Callback roomcallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

    public static synchronized RoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, "WIFI-SK-TEAM")
                    .setJournalMode(JournalMode.AUTOMATIC).allowMainThreadQueries()
                    .addCallback(roomcallback).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}