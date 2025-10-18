package com.octahedron.data.db

import com.octahedron.data.db.table.Albums
import com.octahedron.data.db.table.Artists
import com.octahedron.data.db.table.Tracks
import com.octahedron.data.db.table.TracksAlbums
import com.octahedron.data.db.table.TracksArtists
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Db {

    fun connect(dbPath: String) {
        Class.forName("org.sqlite.JDBC")

        Database.connect(
            url = "jdbc:sqlite:$dbPath",
            driver = "org.sqlite.JDBC"
        )
    }

    fun migrate() = transaction {
        SchemaUtils.createMissingTablesAndColumns(Tracks, Artists, Albums, TracksAlbums, TracksArtists)
    }
}