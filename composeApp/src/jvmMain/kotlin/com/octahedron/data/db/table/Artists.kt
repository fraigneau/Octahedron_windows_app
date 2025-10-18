package com.octahedron.data.db.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Artists : LongIdTable("artists") {

    val name = varchar("name", 256)

    init {
        index(false, name)
    }

}