package com.octahedron.data.model

data class TrackAlbum(
    val trackId: Long,
    val albumId: Long
) {
    init {
        require(trackId >= 0) { "Track ID cannot be less than 0." }
        require(albumId >= 0) { "Album ID cannot be less than 0." }
    }
}
