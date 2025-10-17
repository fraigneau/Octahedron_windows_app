package com.octahedron.data.mapper

import org.jetbrains.exposed.sql.ResultRow
import com.octahedron.data.db.table.*
import com.octahedron.data.model.*

fun ResultRow.toTrack() = Track(
    uid = this[Tracks.id].value,
    title = this[Tracks.title],
    duration = this[Tracks.duration]
)
