package me.hashcode.dawadeals.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import me.hashcode.dawadeals.data.SearchEntry;

@androidx.room.Database(entities = {SearchEntry.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager myDataBase;

    public static DatabaseManager getInstance() {

        return myDataBase;
    }

    public static void setInstance(Context context) {
        myDataBase = Room.databaseBuilder(context.getApplicationContext(),
                DatabaseManager.class, "dawa_deals")
                .allowMainThreadQueries()//todo remove this and use thread
                .build();

    }

    public abstract SearchDao searchDao();


}