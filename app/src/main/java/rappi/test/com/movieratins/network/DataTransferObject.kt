package rappi.test.com.movieratins.network

import com.example.android.devbyteviewer.database.DatabaseMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import rappi.test.com.movieratins.domain.Movie

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

/**
 * VideoHolder holds a list of Videos.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "movie": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val results: List<Results>)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class Results(
    val id: Long,
    val overview: String,
    val title: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "vote_average") val voteAverage: Float = 0f,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String
)

/**
 * Convert Network results to database objects
 */
fun NetworkMovieContainer.asDomainModel(): List<Movie> {
    return results.map {
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

fun NetworkMovieContainer.asDatabaseModel(): Array<DatabaseMovie> {
    return results.map {
        DatabaseMovie(
            id = it.id,
            overview = it.overview,
            releaseDate = it.releaseDate,
            title = it.title,
            backdropPath = it.backdropPath,
            voteAverage = it.voteAverage,
            posterPath = it.posterPath
        )
    }.toTypedArray()
}