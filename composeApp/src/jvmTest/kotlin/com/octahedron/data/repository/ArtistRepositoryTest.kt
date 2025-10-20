package com.octahedron.data.repository

import com.octahedron.data.db.Db
import com.octahedron.data.db.table.Artists
import com.octahedron.data.model.Artist
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArtistRepositoryTest {

    val repo = ArtistRepository

    @BeforeAll
    fun boot() {
        Db.connect()
    }

    @BeforeEach
    fun setup() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Artists)
            Artists.deleteAll()
        }
    }

    @AfterEach
    fun teardown() {
        transaction {
            SchemaUtils.drop(Artists)
        }
    }

    fun insertSampleArtist(): Artist? {
        val artist = Artist(
            uid = 1L,
            name = "Sample Artist")
        return repo.insert(artist)
    }

    @Test
    fun testInsert() {
        insertSampleArtist()
        repo.get(1L)?.let {
            Assertions.assertEquals("Sample Artist", it.name)
        } ?: Assertions.fail("Artist not found")
    }

    @Test
    fun testDelete() {
        insertSampleArtist()
        val deleted = repo.delete(1L)
        Assertions.assertTrue(deleted)
        val artist = repo.get(1L)
        Assertions.assertNull(artist)
    }

    @Test
    fun testGetList() {
        insertSampleArtist()
        val artists = repo.list()
        Assertions.assertEquals(1, artists.size)
        Assertions.assertEquals("Sample Artist", artists[0].name)
    }
}