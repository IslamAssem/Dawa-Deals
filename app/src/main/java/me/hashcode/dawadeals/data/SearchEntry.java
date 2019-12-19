package me.hashcode.dawadeals.data;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class SearchEntry {
    @PrimaryKey(autoGenerate = true)
    int id;
    String search_text;

    public SearchEntry() {
    }

    @Ignore
    public SearchEntry(String search_text) {
        this.search_text = search_text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearch_text() {
        return search_text;
    }

    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof SearchEntry)
            return id == ((SearchEntry) obj).id;
        return false;
    }
}
