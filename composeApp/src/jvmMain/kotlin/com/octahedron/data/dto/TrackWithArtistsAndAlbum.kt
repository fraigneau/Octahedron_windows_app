package com.octahedron.data.dto

data class TrackWithArtistsAndAlbum(
    val track: TrackDTO,
    val artistes: List<ArtistDTO>,
    val album: AlbumDTO?
)
