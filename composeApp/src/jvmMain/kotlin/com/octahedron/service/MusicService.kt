package com.octahedron.service

import com.octahedron.data.model.Album
import com.octahedron.data.model.Artist
import com.octahedron.data.model.Track
import com.octahedron.data.nowPlaying.NowPlayingBus
import com.octahedron.data.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

object MusicService {

    lateinit var trackRepo: TrackRepository
    lateinit var lhRepo: ListeningHistoryRepository
    lateinit var artistRepo: ArtistRepository
    lateinit var trackArtistsRepo: TrackArtistsRepository
    lateinit var albumRepo: AlbumRepository
    lateinit var trackAlbumRepo: TrackAlbumRepository

    suspend fun saveNowPlaying(np: NowPlayingBus): Boolean = withContext(Dispatchers.IO) {
        transaction {
            try {
                val track = Track(title = np.title, duration = np.duration.toLong())
                val artists: List<Artist> = np.artist.map { Artist(name = it) }
                val album = Album(title = np.album)

                val trackId = trackRepo.insert(track)
                val albumId = albumRepo.insert(album)

                artists.forEachIndexed { index, artist ->
                    val artistId = artistRepo.insert(artist).uid
                    trackArtistsRepo.addLink(track.uid, artistId, isMain = index == 0)
                }

                trackAlbumRepo.addLink(track.uid, albumId.uid)

                lhRepo.insert(trackId.uid)
                return@transaction true
            } catch (e: Exception) {
                false
            }
        }
    }
}