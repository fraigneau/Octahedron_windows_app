package com.octahedron.data.repository

import com.octahedron.data.db.table.Tracks
import com.octahedron.data.mapper.toTrack
import com.octahedron.data.model.Track
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TrackRepository {
    fun insert(title: String, duration: Long): Track? = transaction {
        val existing = Tracks.selectAll().where { Tracks.title eq title }.singleOrNull()
        if (existing != null) {
            null
        } else {
            val id = Tracks.insertAndGetId {
                it[Tracks.title] = title
                it[Tracks.duration] = duration
            }.value
            Track(id, title, duration)
        }
    }
    fun get(id: Long): Track? = transaction {
        Tracks.selectAll().where(Tracks.id eq id).singleOrNull()?.toTrack()
    }
    fun list(): List<Track> = transaction {
        Tracks.selectAll().map { it.toTrack() }
    }
    fun delete(id: Long): Boolean = transaction {
        Tracks.deleteWhere { Tracks.id eq id } > 0
    }
}