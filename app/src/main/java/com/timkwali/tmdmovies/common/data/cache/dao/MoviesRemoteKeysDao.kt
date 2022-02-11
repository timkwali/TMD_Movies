package com.timkwali.tmdmovies.common.data.cache.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timkwali.tmdmovies.common.data.model.remotekeys.MoviesRemoteKeys

interface MoviesRemoteKeysDao {

    @Query("SELECT * FROM movies_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): MoviesRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllRemoteKeys(remoteKeys: List<MoviesRemoteKeys>)

    @Query("DELETE FROM movies_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}