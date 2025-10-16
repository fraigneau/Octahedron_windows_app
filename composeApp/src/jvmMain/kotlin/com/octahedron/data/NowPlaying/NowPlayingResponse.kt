package com.octahedron.data.NowPlaying

import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingResponse(
    val ok: Boolean,
    val err: String? = null,
    val data: NowPlayingData? = null
)
