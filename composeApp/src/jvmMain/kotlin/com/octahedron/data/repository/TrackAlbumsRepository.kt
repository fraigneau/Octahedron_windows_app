package com.octahedron.data.repository

import com.octahedron.data.db.table.Albums
import com.octahedron.data.db.table.TracksAlbums
import com.octahedron.data.db.table.TracksArtists
import com.octahedron.data.mapper.toAlbum
import com.octahedron.data.model.Album
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TrackAlbumsRepository {

    fun addLink(trackId: Long, artistId: Long) = transaction {
        TracksArtists.insertIgnore {
            it[TracksArtists.trackId] = trackId
            it[TracksArtists.artistId] = artistId
        }
    }
    fun getAlbumsForTrack(trackId: Long): Album? = transaction {
        (TracksAlbums innerJoin Albums)
            .selectAll().where { TracksAlbums.trackId eq trackId }
            .singleOrNull()
            ?.toAlbum()
    }
    fun removeLink(trackId: Long): Boolean = transaction {
        TracksAlbums.deleteWhere { TracksAlbums.trackId eq trackId } > 0
    }
}