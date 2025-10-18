package com.octahedron.data.dto

data class TrackWithArtists(
    val track: TrackDTO,
    val artistes: List<ArtistDTO>
)
