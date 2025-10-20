package com.octahedron.data.model

data class ListeningHistory(
    val trackId: Long,
    val listenedAt: Long
) {
    init {
        require(trackId >= 0) { "Track ID cannot be less than 0." }
        require(listenedAt > 0) { "Listened at timestamp must be greater than 0." }
    }
}
