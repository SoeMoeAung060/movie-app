package com.soe.movieticketapp.data.local.Dao.trending

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soe.movieticketapp.data.local.entity.trending.TrendingMovieRemoteKeyEntity

@Dao
interface TrendingMovieDaoRemoteKey {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(remoteKey: List<TrendingMovieRemoteKeyEntity>)

    @Query("SELECT * FROM remoteKeys WHERE id = :id")
     fun getRemoteKeyByMovieId(id: Int): TrendingMovieRemoteKeyEntity?

    @Query("DELETE FROM remoteKeys")
     fun clearRemoteKeys()


}