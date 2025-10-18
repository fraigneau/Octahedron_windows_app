package com.octahedron.data.repository

import com.octahedron.data.db.table.Artists
import com.octahedron.data.mapper.toArtist
import com.octahedron.data.model.Artist
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object ArtistRepository {
    fun insert(artist: Artist): Artist? = transaction {
        val existing = Artists.selectAll().where { Artists.name eq artist.name }.singleOrNull()
        if (existing != null) {
            null
        } else {
            val id = Artists.insertAndGetId {
                it[Artists.name] = artist.name
            }.value
            Artist(id, artist.name)
        }
    }
    fun get(id: Long): Artist? = transaction {
        Artists.selectAll().where(Artists.id eq id).singleOrNull()?.toArtist()
    }
    fun list(): List<Artist> = transaction {
        Artists.selectAll().map { it.toArtist() }
    }
    fun delete(id: Long): Boolean = transaction {
        Artists.deleteWhere { Artists.id eq id } > 0
    }
}