package com.octahedron.data.repository

import com.octahedron.data.db.table.Artists
import com.octahedron.data.db.table.TracksArtists
import com.octahedron.data.mapper.toArtist
import com.octahedron.data.model.Artist
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TrackArtistsRepository {

    fun addLink(trackId: Long, artistId: Long) = transaction {
        TracksArtists.insertIgnore {
            it[TracksArtists.trackId] = trackId
            it[TracksArtists.artistId] = artistId
        }
    }
    fun getArtistsForTrack(trackId: Long): List<Artist> = transaction {
        (TracksArtists innerJoin Artists)
            .selectAll().where { TracksArtists.trackId eq trackId }
            .map { it.toArtist() }
    }
    fun removeLinks(trackId: Long) = transaction {
        TracksArtists.deleteWhere { TracksArtists.trackId eq trackId }
    }
}