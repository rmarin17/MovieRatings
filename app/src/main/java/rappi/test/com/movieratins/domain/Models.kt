package rappi.test.com.movieratins.domain

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import rappi.test.com.movieratins.util.smartTruncate

/**
 * Movie represent a results that can be displayed.
 */

data class Movie(
    var id: Long,
    var posterPath: String,
    var overview: String,
    var releaseDate: String,
    var title: String,
    var backdropPath: String,
    var voteAverage: Float = 0f,
    var popularity:Float = 0f
)
