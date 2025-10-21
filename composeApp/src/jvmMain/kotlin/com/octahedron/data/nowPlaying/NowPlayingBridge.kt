package com.octahedron.data.nowPlaying

import com.octahedron.data.repository.TrackRepository
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object NowPlayingBridge {
    private var process: Process? = null
    private var job: Job? = null
    private val json = Json { ignoreUnknownKeys = true }

    private fun runtimeDir(): File {
        val base = System.getenv("LOCALAPPDATA") ?: System.getenv("APPDATA") ?: error("Pas d'AppData")
        return File(base, "Octahedron/runtime").apply { mkdirs() }
    }

    private fun purgeOldExecutables(keep: File? = null) {
        val dir = runtimeDir()
        dir.listFiles { f -> f.isFile && f.name.startsWith("NowPlayingBridge-") && f.name.endsWith(".exe") }
            ?.forEach { f -> if (keep == null || f.absolutePath != keep.absolutePath) runCatching { f.delete() } }
    }

    private fun extractEphemeral(): File {
        val dir = runtimeDir().toPath()
        val tmp = Files.createTempFile(dir, "NowPlayingBridge-", ".exe")
        NowPlayingBridge::class.java.getResourceAsStream("/bin/NowPlayingBridge.exe")
            ?.use { Files.copy(it, tmp, StandardCopyOption.REPLACE_EXISTING) }
            ?: error("Ressource introuvable")
        val f = tmp.toFile()
        require(f.exists() && f.length() > 0) { "Extraction invalide" }
        return f
    }

    fun start(onUpdate: (NowPlayingData?) -> Unit) {
        if (process?.isAlive == true) return
        purgeOldExecutables()
        val exe = runCatching { extractEphemeral() }.getOrElse { return }
        process = runCatching {
            ProcessBuilder(exe.absolutePath).directory(exe.parentFile).redirectErrorStream(true).start()
        }.getOrNull() ?: return
        println("NowPlayingBridge: started, pid=${process?.pid()}")

        job = CoroutineScope(Dispatchers.IO).launch {
            process?.inputStream?.bufferedReader(Charsets.UTF_8)?.useLines { lines ->
                for (line in lines) {
                    if (line.isBlank()) continue
                    runCatching {
                        val resp = json.decodeFromString<NowPlayingResponse>(line)
                        onUpdate(resp.data)
                        println("NowPlayingBridge: $resp")
                        if (resp.data != null)
                            TrackRepository.insert(resp.data.title.toString(), resp.data.durationSeconds?.toLong() ?: 0L)
                    }
                }
            }
        }

    }

    fun stop() {
        runCatching { process?.destroy() }
        job?.cancel()
        process = null
        purgeOldExecutables()
    }
}
