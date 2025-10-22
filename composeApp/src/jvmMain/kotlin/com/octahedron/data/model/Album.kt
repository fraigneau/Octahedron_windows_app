package com.octahedron.data.model

data class Album(
    val uid: Long = 0L,
    var title: String
) {
    init {
        require(uid >= 0) { "Artist uid cannot be less than 0." }
        require(title.isNotBlank()) { "Album title cannot be empty." }
    }
}