package com.stonkdelay.config
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File



class Config(private val config: File) {
    var settings: Settings = Settings()

    fun load() {
        if (!config.exists()) {
            config.createNewFile()
            return
        }
        settings = config.runCatching {
            Json.decodeFromString<Settings>(this.readText())
        }.getOrNull() ?: Settings()
    }

    fun save() {
        val serialized = Json.encodeToString(settings)
        config.writeText(serialized)
    }

    @Serializable
    data class Settings(var enabled: Boolean = false, var delay: Int = 0)
}