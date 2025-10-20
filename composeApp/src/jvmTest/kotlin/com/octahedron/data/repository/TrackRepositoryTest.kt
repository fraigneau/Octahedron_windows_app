package com.octahedron.data.repository

import com.octahedron.data.db.Db
import com.octahedron.data.db.table.Tracks
import com.octahedron.data.model.Track
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrackRepositoryTest {

    val repo = TrackRepository

    @BeforeAll
    fun boot() {
        Db.connect()
    }

    @BeforeEach
    fun setup() {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Tracks)
            Tracks.deleteAll()
        }
    }

    @AfterEach
    fun teardown() {
        transaction {
            SchemaUtils.drop(Tracks)
        }
    }

    fun insertSampleTrack(): Track? {
        return repo.insert("Sample Track", 180_000L)
    }

    @Test
    fun testInsert() {
        insertSampleTrack()
        repo.get(1L)?.let {
            Assertions.assertEquals("Sample Track", it.title)
            Assertions.assertEquals(180_000L, it.duration)
        } ?: Assertions.fail("Track not found")
    }

    @Test
    fun testDelete() {
        insertSampleTrack()
        val deleted = repo.delete(1L)
        Assertions.assertTrue(deleted)
        val track = repo.get(1L)
        Assertions.assertNull(track)
    }

    @Test
    fun testGetList() {
        insertSampleTrack()
        val tracks = repo.list()
        Assertions.assertEquals(1, tracks.size)
        Assertions.assertEquals("Sample Track", tracks[0].title)
        Assertions.assertEquals(180_000L, tracks[0].duration)
    }
}