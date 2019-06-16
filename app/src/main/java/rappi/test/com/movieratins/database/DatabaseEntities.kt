package com.example.android.devbyteviewer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import rappi.test.com.movieratins.domain.Movie

@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Long,
    val overview: String,
    val releaseDate: String,
    val title: String,
    val backdropPath: String,
    val voteAverage: Float = 0f,
    val posterPath: String
)

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            overview = it.overview,
            releaseDate = it.releaseDate,
            title = it.title,
            backdropPath = it.backdropPath,
            voteAverage = it.voteAverage,
            posterPath = it.posterPath
        )
    }
}
