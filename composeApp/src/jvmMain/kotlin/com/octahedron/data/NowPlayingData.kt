package com.octahedron.data

import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingData(
    val app: String? = null,
    val title: String? = null,
    val artist: String? = null,
    val album: String? = null,
    val coverPath: String? = null,
    val playback: String? = null,
    val timelinePosition: Double? = null,
    val durationSeconds: Double? = null
)
