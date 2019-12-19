package me.hashcode.dawadeals.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import me.hashcode.dawadeals.data.SearchEntry;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SearchDao {
    @Insert(onConflict = REPLACE)
    void insert(SearchEntry searchEntries);

    @Insert(onConflict = REPLACE)
    void insert(List<SearchEntry> searchEntries);

    @Update(onConflict = REPLACE)
    void update(List<SearchEntry> searchEntries);

    @Delete()
    void delete(List<SearchEntry> searchEntries);

    @Delete()
    void delete(SearchEntry searchEntries);

    @Query("select * from SearchEntry")
    Single<List<SearchEntry>> select();
}
