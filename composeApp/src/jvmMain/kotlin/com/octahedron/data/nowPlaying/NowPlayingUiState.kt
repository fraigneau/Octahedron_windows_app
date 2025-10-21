package com.octahedron.data.nowPlaying

import org.jetbrains.skia.Bitmap
import java.time.Instant

data class NowPlayingUiState(
    val title: String? = null,
    val artist: String? = null,
    val album: String? = null,
    val bitmap: Bitmap? = null,
    val isPlaying: Boolean = false,
    val positionMs: Long? = null,
    val durationMs: Long? = null,
    val updatedAt: Instant? = null,
)
