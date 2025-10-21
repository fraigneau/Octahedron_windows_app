package com.octahedron.data.mapper

import org.jetbrains.exposed.sql.ResultRow
import com.octahedron.data.db.table.*
import com.octahedron.data.model.*

fun ResultRow.toTrack() = Track(
    uid = this[Tracks.id].value,
    title = this[Tracks.title],
    duration = this[Tracks.duration]
)

fun ResultRow.toArtist() = Artist(
    uid = this[Artists.id].value,
    name = this[Artists.name]
)

fun ResultRow.toAlbum() = Album(
    uid = this[Albums.id].value,
    title = this[Albums.title],
)

fun ResultRow.toTrackAlbum() = TrackAlbum(
    trackId = this[TracksAlbums.trackId].value,
    albumId = this[TracksAlbums.albumId].value
)

fun ResultRow.toTrackArtist() = TrackArtists(
    trackId = this[TracksArtists.trackId].value,
    artistId = this[TracksArtists.artistId].value,
    isMain = this[TracksArtists.isMain]
)

fun ResultRow.toListeningHistory() = ListeningHistory(
    trackId = this[ListeningHistories.trackId].value,
    listenedAt = this[ListeningHistories.listenedAt]
)