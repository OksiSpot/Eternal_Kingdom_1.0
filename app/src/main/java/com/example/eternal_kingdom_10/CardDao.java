package com.example.eternal_kingdom_10;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM Card")
    List<Card> getAll();

    @Query("SELECT * FROM Card WHERE id = :id")
    Card getByID(int id);

    @Insert
    void insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

}
