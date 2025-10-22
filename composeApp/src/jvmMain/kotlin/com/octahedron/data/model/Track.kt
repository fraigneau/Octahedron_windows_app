package com.octahedron.data.model

data class Track(
    val uid: Long = 0L,
    var title: String,
    var duration: Long
) {
    init {
        require(title.isNotBlank()) { "Track title cannot be empty." }
        require(duration > 0) { "Track duration cannot be greater than 0." }
    }
}
