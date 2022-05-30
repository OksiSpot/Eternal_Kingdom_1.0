package com.example.eternal_kingdom_10;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Card.class}, version = 1)
public abstract class CardDataBase extends RoomDatabase {
    public abstract CardDao CardDao();

}
