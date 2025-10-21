package com.octahedron.data.dto

import com.octahedron.data.model.Album
import com.octahedron.data.model.Track

data class TrackWithAlbum(
    val track: Track,
    val album: Album
)
