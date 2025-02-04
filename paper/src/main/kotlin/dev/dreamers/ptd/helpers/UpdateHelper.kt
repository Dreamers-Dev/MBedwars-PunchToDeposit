package dev.dreamers.ptd.helpers

import com.google.gson.Gson
import dev.dreamers.ptd.PunchToDeposit
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class UpdateHelper {
    companion object {
        private const val PLUGIN_NAME = "PunchToDeposit"
        private const val PLUGIN_VERSION = "1.0.0"

        @Volatile
        var isOutDated: Boolean = false
            private set

        private var task: BukkitTask? = null

        private val httpClient: HttpClient = HttpClient.newHttpClient()
        private val httpRequest: HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://dreamers.dev/apps/versions.php?name=PunchToDeposit"))
            .GET()
            .build()

        data class ApiResponse(val ok: Boolean, val data: Data, val message: String)
        data class Data(val name: String, val version: String)

        private fun checkForUpdates() {
            task?.cancel()

            task = Bukkit.getScheduler().runTaskTimerAsynchronously(PunchToDeposit.getInst(), Runnable {
                httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply { response ->
                        try {
                            Gson().fromJson(response.body(), ApiResponse::class.java)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    .thenAccept {jsonData ->
                        if (jsonData == null || !jsonData.ok || jsonData.data.name != PLUGIN_NAME) return@thenAccept

                        isOutDated = jsonData.data.version != PLUGIN_VERSION
                    }
            }, 0, 36000)
        }

        fun startUpdateCheck() {
            checkForUpdates()
        }

        fun stopUpdateCheck() {
            task?.cancel()
        }
    }
}
