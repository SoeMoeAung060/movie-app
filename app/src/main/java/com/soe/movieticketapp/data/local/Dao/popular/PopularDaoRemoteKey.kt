package com.soe.movieticketapp.data.local.Dao.popular

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soe.movieticketapp.data.local.entity.popular.PopularRemoteKeyEntity

@Dao
interface PopularDaoRemoteKey {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularAll(remoteKey: List<PopularRemoteKeyEntity>)

    @Query("SELECT * FROM popular_movie_table WHERE id = :id")
    fun getRemoteKeyByPopularId(id: Int): PopularRemoteKeyEntity?

    @Query("DELETE FROM popular_movie_table")
    fun clearRemoteKeys()
}