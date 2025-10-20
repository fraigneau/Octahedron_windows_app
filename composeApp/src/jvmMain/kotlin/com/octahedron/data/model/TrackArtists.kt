package com.octahedron.data.model

data class TrackArtists(
    val trackId: Long,
    val artistId: Long,
    val role: String
) {
    init {
        require(trackId >= 0) { "Track ID cannot be less than 0." }
        require(artistId >= 0) { "Artist ID cannot be less than 0." }
        require(role.isNotBlank()) { "Artist role cannot be empty." }
        require(role == "main" || role == "feat") { "Artist role must be either 'main' or 'feat'." }
    }
}
