package com.soe.movieticketapp.data.mapper

import com.soe.movieticketapp.data.remote.dto.DetailResponseDTO
import com.soe.movieticketapp.domain.model.Detail
import com.soe.movieticketapp.domain.model.Movie


fun DetailResponseDTO.toDetail() : Detail{
    return Detail(
        budget = this.budget,
        homepage = this.homepage,
        id = this.id,
        imdbId = this.imdbId,
        originalTitle = this.originalTitle,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        posterPath = this.posterPath,
        popularity = this.popularity,
        revenue = this.revenue,
        runtime = this.runtime,
        releaseDate = this.releaseDate,
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        genres = this.genres
    )
}




//fun TrendingResponseDTO.toTrending() : Trending{
//    return Trending(
//        id = id,
//        overview = overview,
//        posterPath = posterPath,
//        releaseDate = releaseDate,
//        title = title,
//        voteAverage = voteAverage,
//        adult = adult,
//        backdropPath = backdropPath ?: "",
//        firstAirDate = firstAirDate ?: "",
//        genreIds = genreIds,
//        mediaType = mediaType ?: "",
//        name = name ?: "",
//        originalLanguage = originalLanguage,
//        originalName = originalName ?: "",
//        originalTitle = originalTitle,
//        popularity = popularity,
//        video = video,
//        voteCount = voteCount,
//        originCountry = originCountry ?: emptyList(),
//
//
//        )
//}

//fun MovieResponseDTO.toPopularEntity(category: String) : List<PopularEntity>{
//    return results.map {
//        PopularEntity(
//            adult = it.adult,
//            backdropPath = it.backdropPath ?: "",
//            genreIds = it.genreIds,
//            id = it.id,
//            originalLanguage = it.originalLanguage,
//            overview = it.overview,
//            originalTitle = it.originalTitle,
//            popularity = it.popularity,
//            posterPath = it.posterPath,
//            releaseDate = it.releaseDate,
//            title = it.title,
//            video = it.video,
//            voteAverage = it.voteAverage ?: 0.0,
//            voteCount = it.voteCount,
//            category = category
//        )
//    }
//}


//fun PopularEntity.toDomainPopularMovie() : Popular{
//    return Popular(
//        id = this.id,
//        adult = this.adult,
//        backdropPath = this.backdropPath,
//        genreIds = this.genreIds,
//        originalLanguage = this.originalLanguage,
//        overview = this.overview,
//        originalTitle = this.originalTitle,
//        title = this.title,
//        popularity = this.popularity,
//        posterPath = this.posterPath,
//        releaseDate = this.releaseDate,
//        video = this.video,
//        voteAverage = this.voteAverage,
//        voteCount = this.voteCount
//    )
//}
//
//fun TrendingMovieEntity.toDomainMovie() : Trending{
//    return Trending(
//        id = this.id,
//        posterPath = this.posterPath,
//        overview = this.overview,
//        releaseDate = this.releaseDate,
//        title = this.title,
//        voteAverage = this.voteAverage,
//        adult = this.adult,
//        backdropPath = this.backdropPath,
//        firstAirDate = this.firstAirDate,
//        genreIds = this.genreIds,
//        mediaType = this.mediaType,
//        name = this.name,
//        originalLanguage = this.originalLanguage,
//        originalName = this.originalName,
//        originalTitle = this.originalTitle,
//        popularity = this.popularity,
//        video = this.video,
//        voteCount = this.voteCount,
//        originCountry = this.originCountry
//    )
//}