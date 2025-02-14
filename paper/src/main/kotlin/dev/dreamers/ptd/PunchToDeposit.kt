package dev.dreamers.ptd

import dev.dreamers.ptd.commands.PTDCommand
import dev.dreamers.ptd.helpers.HookHelper
import dev.dreamers.ptd.helpers.MessageHelper
import dev.dreamers.ptd.helpers.UpdateHelper
import dev.dreamers.ptd.listeners.InteractListener
import dev.dreamers.ptd.listeners.JoinListener
import dev.dreamers.ptd.services.ConfigService
import dev.dreamers.ptd.services.LogService
import dev.dreamers.ptd.services.MessageService
import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PunchToDeposit : JavaPlugin() {
    companion object {
        private lateinit var plugin: PunchToDeposit
        private lateinit var metrics: Metrics

        fun getInst(): PunchToDeposit {
            return plugin
        }
    }

    override fun onEnable() {
        try {
            plugin = this

            ConfigService.init()
            MessageService.init()

            MessageHelper.printSplashScreen()
            LogService.info("Loaded Config")
            LogService.info("Loaded Messages")

            HookHelper.init()

            Bukkit.getPluginManager().registerEvents(InteractListener(), this)
            Bukkit.getPluginManager().registerEvents(JoinListener(), this)
            LogService.info("Registered Events")

            getCommand("ptd")?.setExecutor(PTDCommand())
            LogService.info("Registered Commands")

            metrics = Metrics(this, 24460)
            metrics.addCustomChart(SimplePie("uses_bedwars") {
                if (HookHelper.usesMBedwars) "true" else "false"
            })
            LogService.info("Initialized metrics")

            UpdateHelper.startUpdateCheck()
            LogService.info("                    ")
        } catch (e: Exception) {
            e.printStackTrace()
            Bukkit.getPluginManager().disablePlugin(this)
        }
    }

    override fun onDisable() {
        metrics.shutdown()
    }
}
