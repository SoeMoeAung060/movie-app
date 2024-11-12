package com.soe.movieticketapp.data.local.Dao.popular

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soe.movieticketapp.data.local.entity.popular.PopularEntity

@Dao
interface PopularDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: List<PopularEntity>)

    @Query("SELECT * FROM popular_entity WHERE category = :category ORDER BY id DESC")
    fun getMovieByCategory(category: String) : PagingSource<Int, PopularEntity>

    @Query("DELETE FROM popular_entity")
    fun deleteMovieByCategory()

}