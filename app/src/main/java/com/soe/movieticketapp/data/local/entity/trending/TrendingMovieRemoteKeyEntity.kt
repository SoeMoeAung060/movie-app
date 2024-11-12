package com.soe.movieticketapp.data.local.entity.trending

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKeys")
data class TrendingMovieRemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?

)