package com.octahedron.data.db

import java.nio.file.Files
import java.nio.file.Path

object AppPaths {

    fun dataDir(): Path {
        val base = System.getenv("LOCALAPPDATA") ?: System.getenv("APPDATA") ?: error("Pas d'AppData")
        val p = Path.of(base, "Octahedron", "db")
        Files.createDirectories(p); return p
    }
    fun dbPath(): String = dataDir().resolve("octahedron.db").toString()
}