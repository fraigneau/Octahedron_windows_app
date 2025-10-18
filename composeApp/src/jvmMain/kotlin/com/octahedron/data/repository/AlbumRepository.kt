package com.octahedron.data.repository

import com.octahedron.data.db.table.Albums
import com.octahedron.data.mapper.toArtist
import com.octahedron.data.model.Album
import com.octahedron.data.model.Artist
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object AlbumRepository {
    fun insert(artist: Artist): Album = transaction {
        val existing = Albums.selectAll().where { Albums.title eq artist.name }.singleOrNull()
        (if (existing != null) {
            null
        } else {
            val id = Albums.insertAndGetId {
                it[Albums.title] = artist.name
            }.value
            Album(id, artist.name)
        }) as Album
    }
    fun get(id: Long): Artist? = transaction {
        Albums.selectAll().where { Albums.id eq id }.singleOrNull()?.toArtist()
    }
    fun list(): List<Artist> = transaction {
        Albums.selectAll().map { it.toArtist() }
    }
    fun delete(id: Long): Boolean = transaction {
        Albums.deleteWhere { Albums.id eq id } > 0
    }
}