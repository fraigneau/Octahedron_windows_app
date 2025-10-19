package com.octahedron.data.db.table

import org.jetbrains.exposed.sql.Table

object ListeningHistories: Table("listening_histories") {

    val trackId = reference("track_id", Tracks)
    val listenedAt = long("listened_at")

    override val primaryKey = PrimaryKey(trackId, listenedAt, name = "pk_listening_histories")
}