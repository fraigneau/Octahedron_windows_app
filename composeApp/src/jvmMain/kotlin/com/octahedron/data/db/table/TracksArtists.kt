package com.octahedron.data.db.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TracksArtists: Table("tracks_artists") {

    val trackId = reference("track_id", Tracks, onDelete = ReferenceOption.CASCADE)
    val artistId = reference("artist_id", Artists, onDelete = ReferenceOption.CASCADE)
    val role = varchar("role", 4)

    override val primaryKey = PrimaryKey(trackId, artistId, role, name = "pk_tracks_artists")
}