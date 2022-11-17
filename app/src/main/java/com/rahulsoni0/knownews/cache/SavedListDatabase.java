package com.rahulsoni0.knownews.cache;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SavedListEntityModel.class} , version = 1)
public abstract class SavedListDatabase extends RoomDatabase {

    public abstract SavedListDao savedListDao();

    private static SavedListDatabase INSTANCE;

    public static SavedListDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            ,SavedListDatabase.class,"SavedList")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
