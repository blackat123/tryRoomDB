package com.example.tryroomdb.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: historyBelanja)

    @Delete
    fun delete(daftar: historyBelanja)

    @Query("SELECT * FROM historyBelanja ORDER BY id ASC")
    fun selectAll(): MutableList<historyBelanja>
}