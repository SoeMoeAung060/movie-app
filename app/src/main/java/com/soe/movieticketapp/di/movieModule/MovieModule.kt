package com.soe.movieticketapp.di.movieModule

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.soe.movieticketapp.data.local.Dao.trending.TrendingMovieDao
import com.soe.movieticketapp.data.local.Dao.popular.PopularDao
import com.soe.movieticketapp.data.local.MovieDatabase
import com.soe.movieticketapp.data.local.MovieTypeConverter
import com.soe.movieticketapp.data.remote.api.MovieApi
import com.soe.movieticketapp.data.repository.MovieRepositoryImpl
import com.soe.movieticketapp.domain.repository.MovieRepository
import com.soe.movieticketapp.domain.usecase.GetCastAndCrewMovies
import com.soe.movieticketapp.domain.usecase.GetDetailMovies
import com.soe.movieticketapp.domain.usecase.GetGenreMovies
import com.soe.movieticketapp.domain.usecase.GetNowPlayingMovies
import com.soe.movieticketapp.domain.usecase.GetPopularMovies
import com.soe.movieticketapp.domain.usecase.GetSearchMovies
import com.soe.movieticketapp.domain.usecase.GetSimilarMoviesAndTvSeries
import com.soe.movieticketapp.domain.usecase.GetTopRatedMovies
import com.soe.movieticketapp.domain.usecase.GetTrailersMovie
import com.soe.movieticketapp.domain.usecase.GetTrendingMovies
import com.soe.movieticketapp.domain.usecase.GetUpcomingMovies
import com.soe.movieticketapp.domain.usecase.GetWatchProviders
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.BASE_URL
import com.soe.movieticketapp.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieModule {


    @Provides
    @Singleton
    fun provideMovieApi() : MovieApi{
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


        val json = Json{
            ignoreUnknownKeys = true // Ignore unknown keys
            isLenient = true // Allow lenient parsing
            coerceInputValues = true // Coerce nulls to defaults if property has a default value
        }



        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MovieApi::class.java)
    }


    @Provides
    @Singleton
    fun provideTypeConverter() : MovieTypeConverter = MovieTypeConverter()


    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ) : MovieDatabase = Room.databaseBuilder(
        context = context.applicationContext,
        klass = MovieDatabase::class.java,
        name = DATABASE_NAME
    ).addTypeConverter(MovieTypeConverter())
        .fallbackToDestructiveMigrationFrom()
        .build()


    @Provides
    @Singleton
    fun provideMovieDao(
        movieDatabase: MovieDatabase
    ) : TrendingMovieDao = movieDatabase.movieDao()

    @Provides
    @Singleton
    fun providePopularDao(
        movieDatabase: MovieDatabase
    ) : PopularDao = movieDatabase.popularDao()

    @CategoryQualifier
    @Provides
    fun provideCategory() : String = "popular"

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: MovieApi,
    ) : MovieRepository = MovieRepositoryImpl(
        movieApi = movieApi,
    )


    @Provides
    @Singleton
    fun provideMovieUseCase(
        movieRepository: MovieRepository
    ): MovieUseCase {
        return MovieUseCase(
            getTrendingMovies = GetTrendingMovies(movieRepository),
            getCastAndCrewMovies = GetCastAndCrewMovies(movieRepository),
            getGenreMovies = GetGenreMovies(movieRepository),
            getSimilarMoviesAndTvSeries = GetSimilarMoviesAndTvSeries(movieRepository),
            getWatchProviders = GetWatchProviders(movieRepository),
            getTrailersMovie = GetTrailersMovie(movieRepository),
            getDetailMovies = GetDetailMovies(movieRepository),
            getSearchMovies = GetSearchMovies(movieRepository),
            getNowPlayingMovies = GetNowPlayingMovies(movieRepository),
            getUpcomingMovies = GetUpcomingMovies(movieRepository),
            getTopRatedMovies = GetTopRatedMovies(movieRepository),
            getPopularMovies = GetPopularMovies(movieRepository),
        )
    }


}