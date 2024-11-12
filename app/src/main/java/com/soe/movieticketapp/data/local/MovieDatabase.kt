package com.soe.movieticketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soe.movieticketapp.data.local.Dao.trending.TrendingMovieDao
import com.soe.movieticketapp.data.local.Dao.trending.TrendingMovieDaoRemoteKey
import com.soe.movieticketapp.data.local.Dao.popular.PopularDao
import com.soe.movieticketapp.data.local.Dao.popular.PopularDaoRemoteKey
import com.soe.movieticketapp.data.local.entity.trending.TrendingMovieEntity
import com.soe.movieticketapp.data.local.entity.trending.TrendingMovieRemoteKeyEntity
import com.soe.movieticketapp.data.local.entity.popular.PopularEntity
import com.soe.movieticketapp.data.local.entity.popular.PopularRemoteKeyEntity

@Database(entities = [
    TrendingMovieEntity::class,
    TrendingMovieRemoteKeyEntity::class,
    PopularEntity::class,
    PopularRemoteKeyEntity::class],
    version = 1
)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): TrendingMovieDao
    abstract fun movieDaoRemoteKey() : TrendingMovieDaoRemoteKey


    abstract fun popularDao(): PopularDao
    abstract fun popularDaoRemoteKey() : PopularDaoRemoteKey

}