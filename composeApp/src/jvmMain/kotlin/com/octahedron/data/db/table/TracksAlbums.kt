package com.octahedron.data.db.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TracksAlbums: Table("tracks_albums") {

    val trackId = reference("track_id", Tracks, onDelete = ReferenceOption.CASCADE)
    val albumId = reference("album_id", Albums, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(trackId, albumId, name = "pk_tracks_albums")
}