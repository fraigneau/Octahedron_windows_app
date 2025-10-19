package com.octahedron.data.repository

import com.octahedron.data.db.table.ListeningHistories
import com.octahedron.data.db.table.Tracks
import com.octahedron.data.mapper.toListeningHistory
import com.octahedron.data.model.ListeningHistory
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.to

object ListeningHistoryRepository {

    fun insert(trackId: Long, listenedAt: Long = System.currentTimeMillis()): ListeningHistory = transaction {
        ListeningHistories.insert {
            it[Tracks.id] = EntityID(trackId, Tracks)
            it[ListeningHistories.listenedAt] = listenedAt
        }
        ListeningHistory(trackId, listenedAt)
    }

    fun get(trackId: Long, listenedAt: Long): ListeningHistory? = transaction {
        ListeningHistories
            .selectAll().where {
                (ListeningHistories.trackId eq EntityID(
                    trackId,
                    Tracks
                )) and (ListeningHistories.listenedAt eq listenedAt)
            }
            .singleOrNull()
            ?.toListeningHistory()
    }

    fun listByTrack(trackId: Long, limit: Int = 100, offset: Long = 0): List<ListeningHistory> = transaction {
        ListeningHistories
            .selectAll().where { ListeningHistories.trackId eq EntityID(trackId, Tracks) }
            .orderBy(ListeningHistories.listenedAt to SortOrder.DESC)
            .limit(limit)
            .offset(offset)
            .map { it.toListeningHistory() }
    }

    fun delete(trackId: Long, listenedAt: Long): Boolean = transaction {
        ListeningHistories.deleteWhere {
            (ListeningHistories.trackId eq EntityID(trackId, Tracks)) and
                    (ListeningHistories.listenedAt eq listenedAt)
        } > 0
    }
}