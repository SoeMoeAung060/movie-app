package com.soe.movieticketapp.data.local.entity.popular

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movie_table")
data class PopularRemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
)