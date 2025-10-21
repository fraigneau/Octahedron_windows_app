package com.octahedron.ui.viewmodel

import com.octahedron.data.nowPlaying.NowPlayingBridge
import com.octahedron.data.nowPlaying.NowPlayingUiState
import com.octahedron.ui.helper.loadBitmap
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.Instant

object NowPlayingVM {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _state = MutableStateFlow(NowPlayingUiState())
    val state = _state.asStateFlow()

    fun start() {
        NowPlayingBridge.start { data ->
            if (data == null) return@start
            _state.value = _state.value.copy(
                title = data.title,
                artist = data.artist,
                album = data.album,
                bitmap = loadBitmap(data.coverPath),
                isPlaying = data.playback == "playing",
                positionMs = data.timelinePosition?.times(1000)?.toLong(),
                durationMs = data.durationSeconds?.times(1000)?.toLong(),
                updatedAt = Instant.now(),
            )
        }
    }

    fun stop() {
        NowPlayingBridge.stop()
        scope.cancel()
    }
}