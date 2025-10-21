package com.octahedron.data.model

data class TrackArtists(
    val trackId: Long,
    val artistId: Long,
    val isMain: Boolean
) {
    init {
        require(trackId >= 0) { "Track ID cannot be less than 0." }
        require(artistId >= 0) { "Artist ID cannot be less than 0." }
    }
}
