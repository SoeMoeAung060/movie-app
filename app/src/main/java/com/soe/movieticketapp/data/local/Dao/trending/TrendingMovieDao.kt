package com.soe.movieticketapp.data.local.Dao.trending

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soe.movieticketapp.data.local.entity.trending.TrendingMovieEntity

@Dao
interface TrendingMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertMovie(trendingMovieEntity: List<TrendingMovieEntity>)

    @Query("SELECT * FROM movies WHERE category = :category ORDER BY id DESC")
     fun getMovieByCategory(category: String) : PagingSource<Int, TrendingMovieEntity>

    @Query("DELETE FROM movies")
     fun deleteMovieByCategory()






}