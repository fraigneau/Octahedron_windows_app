package com.octahedron.data.repository

import com.octahedron.data.db.table.Albums
import com.octahedron.data.mapper.toAlbum
import com.octahedron.data.model.Album
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object AlbumRepository {
    fun insert(album: Album): Album = transaction {
        val existing = Albums.selectAll().where { Albums.title eq album.title }.singleOrNull()
        (if (existing != null) {
            null
        } else {
            val id = Albums.insertAndGetId {
                it[Albums.title] = album.title
            }.value
            Album(id, album.title)
        }) as Album
    }
    fun get(id: Long): Album? = transaction {
        Albums.selectAll().where { Albums.id eq id }.singleOrNull()?.toAlbum()
    }
    fun list(): List<Album> = transaction {
        Albums.selectAll().map { it.toAlbum() }
    }
    fun delete(id: Long): Boolean = transaction {
        Albums.deleteWhere { Albums.id eq id } > 0
    }
}