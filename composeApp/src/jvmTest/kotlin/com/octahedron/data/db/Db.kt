package com.octahedron.data.db

import com.octahedron.data.db.table.Albums
import com.octahedron.data.db.table.Artists
import com.octahedron.data.db.table.ListeningHistories
import com.octahedron.data.db.table.Tracks
import com.octahedron.data.db.table.TracksAlbums
import com.octahedron.data.db.table.TracksArtists
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Db {
    fun connect() {
        Database.connect("jdbc:sqlite:./app.db?foreign_keys=on", driver = "org.sqlite.JDBC")
    }

    fun migrate() = transaction {
        SchemaUtils.createMissingTablesAndColumns(Artists, Albums, Tracks, TracksAlbums, TracksArtists, ListeningHistories)
    }

    fun dropAll() = transaction {
        SchemaUtils.drop(ListeningHistories, TracksAlbums, TracksArtists, Tracks, Albums, Artists)
    }
}