package com.rahulsoni0.knownews.cache;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface SavedListDao {

    @Query("SELECT * FROM SavedList ORDER BY uid DESC")
      List<SavedListEntityModel> getAllNews();

    @Insert
    void insertSavedNews(SavedListEntityModel... savedListEntityModels);


    @Delete
    void deleteNews(SavedListEntityModel... savedListEntityModels);

}
