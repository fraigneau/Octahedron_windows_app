package com.octahedron.data.db.table

import org.jetbrains.exposed.dao.id.LongIdTable

object Albums: LongIdTable("albums") {

    val title = varchar("title", 512)
    //val cover: bitmap = bitmap("cover")

    init {
        index(false, title)
    }

}