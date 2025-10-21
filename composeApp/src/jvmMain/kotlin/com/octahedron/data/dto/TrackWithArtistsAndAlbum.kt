package com.octahedron.data.dto

import com.octahedron.data.model.Album
import com.octahedron.data.model.Artist
import com.octahedron.data.model.Track

data class TrackWithArtistsAndAlbum(
    val track: Track,
    val artistes: List<Artist>,
    val album: Album?
)
