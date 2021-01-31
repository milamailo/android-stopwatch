package ca.i3th.stopwatchcode_ver02.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecordDeo {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Record record);

    @Update
    int update(Record record);

    @Delete
    boolean delete(Record records);

    @Query("DELETE FROM Record")
    void deleteAll();

    @Query("SELECT * FROM Record ORDER BY rid DESC")
    LiveData<List<Record>> getAllRecords();
}
