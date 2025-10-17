package com.octahedron.data.db.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Tracks: LongIdTable("tracks") {

    val title = varchar("title", 512)
    val duration = long("duration")

    init {
        index(false, title)
    }
}