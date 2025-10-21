package com.octahedron.data.dto

import com.octahedron.data.model.Artist
import com.octahedron.data.model.Track

data class TrackWithArtists(
    val track: Track,
    val artistes: List<Artist>
)
