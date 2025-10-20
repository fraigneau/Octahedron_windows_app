package com.octahedron.data.repository

import com.octahedron.data.db.Db
import com.octahedron.data.db.table.*
import com.octahedron.data.model.Album
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlbumRepositoryTest {

    val repo = AlbumRepository

    @BeforeAll
    fun boot() {
        Db.connect()
    }

    @BeforeEach
    fun setup() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Albums)
            Albums.deleteAll()
        }
    }

    @AfterEach
    fun teardown() {
        transaction {
            SchemaUtils.drop(Albums)
        }
    }

    fun insertSampleAlbum(): Album {
        val album = Album(
            uid = 1L,
            title = "Sample Album"
        )
        return repo.insert(album)
    }

    @Test
    fun testInsert() {
        insertSampleAlbum()
        repo.get(1L)?.let {
            Assertions.assertEquals("Sample Album", it.title)
        } ?: Assertions.fail("Album not found")
    }

    @Test
    fun testDelete() {
        insertSampleAlbum()
        val deleted = repo.delete(1L)
        Assertions.assertTrue(deleted)
        val album = repo.get(1L)
        Assertions.assertNull(album)
    }

    @Test
    fun testGetList() {
        insertSampleAlbum()
        val albums = repo.list()
        Assertions.assertEquals(1, albums.size)
        Assertions.assertEquals("Sample Album", albums[0].title)
    }
}