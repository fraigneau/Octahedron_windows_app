package com.octahedron.data.nowPlaying

data class NowPlayingBus(
    val title: String,
    val artist: List<String>,
    val album: String,
    val duration: Double
)
