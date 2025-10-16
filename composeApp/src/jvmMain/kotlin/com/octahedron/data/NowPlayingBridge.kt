package com.octahedron.data

import kotlinx.coroutines.Job
import kotlinx.serialization.json.Json
import java.io.File
import kotlinx.coroutines.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption

object NowPlayingBridge {
    private var process: Process? = null
    private var job: Job? = null
    private val json = Json { ignoreUnknownKeys = true }

    private fun targetExe(): File {
        val appData = System.getenv("APPDATA")
            ?: error("Impossible de trouver le dossier AppData")
        val dir = File(appData, "Octahedron/bin")
        if (!dir.exists()) dir.mkdirs()
        return File(dir, "NowPlayingBridge.exe")
    }

    private fun ensureExtracted(): File {
        val target = targetExe()

        runCatching { process?.destroyForcibly() }

        val ins = NowPlayingBridge::class.java.getResourceAsStream("/bin/NowPlayingBridge.exe")
            ?: error("Resource /bin/NowPlayingBridge.exe introuvable")

        val tmp = File(target.parentFile, target.name + ".tmp")
        tmp.outputStream().use { out -> ins.copyTo(out) }

        repeat(5) { i ->
            runCatching {
                Files.move(
                    tmp.toPath(), target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
                )
                return@repeat
            }.onFailure {
                Thread.sleep(200L * (i + 1))
            }
        }

        target.setExecutable(true)
        println("Bridge mis à jour : ${target.absolutePath}")
        return target
    }

    fun start(onUpdate: (NowPlayingData?) -> Unit) {
        if (process?.isAlive == true) return

        runCatching {
            val exe = ensureExtracted()

            process = ProcessBuilder(exe.absolutePath)
                .directory(exe.parentFile)
                .redirectErrorStream(true)
                .start()
        }.onFailure {
            return
        }

        job = CoroutineScope(Dispatchers.IO).launch {
            process?.inputStream?.bufferedReader(Charsets.UTF_8)?.useLines { lines ->
                for (line in lines) {
                    if (line.isBlank()) continue
                    runCatching {
                        val resp = json.decodeFromString<NowPlayingResponse>(line)
                        onUpdate(resp.data)
                        if (resp.data != null) {
                            println("parse ok: $resp" )
                        }
                    }.onFailure {
                        println("parse error: $it\n$line")
                    }
                }
            }
        }
    }

    fun stop() {
        runCatching { process?.destroy() }
        job?.cancel()
        process = null
    }
}