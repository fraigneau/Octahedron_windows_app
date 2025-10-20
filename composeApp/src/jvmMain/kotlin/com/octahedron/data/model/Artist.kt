package com.octahedron.data.model

data class Artist(
    val uid: Long,
    var name: String
) {
    init {
        require(uid >= 0) { "Artist uid cannot be less than 0." }
        require(name.isNotBlank()) { "Artist name cannot be empty." }
    }
}
