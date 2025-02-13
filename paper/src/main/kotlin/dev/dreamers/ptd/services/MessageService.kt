package dev.dreamers.ptd.services

import dev.dreamers.ptd.helpers.MessageHelper
import dev.dreamers.ptd.helpers.StorageHelper

class MessageService private constructor() : StorageHelper("messages.yml") {

    companion object {
        private val instance = MessageService()

        fun init() = instance.init()
        fun reload(): Boolean = instance.reload()

        private fun setPlaceHolders(input: String): String = instance.setPlaceHolders(input)

        var PREFIX: String = MessageHelper.colorize("<dark_gray>[<aqua>PTD<dark_gray>] <reset>")
        var TRANSFER_SUCCESS: String = setPlaceHolders("%prefix <green>✔ <gray>Transferred total of <white>x%amount %item <gray>into %container")
        var ITEM_BLACKLISTED: String = setPlaceHolders("%prefix &c✖ &7That item is &cBlacklisted &7from being deposited")
        var CONTAINER_BLACKLISTED: String = setPlaceHolders("%prefix &c✖ That container is &cBlacklisted &7from accepting items as deposits")
        var UPDATE_FOUND: String = setPlaceHolders("%prefix <red>⚠ <click:OPEN_URL:https://github.com/Dreamers-Dev/PunchToDeposit/releases><gray>A new update is available. Please <green>Update <gray>to the latest version.</click>")
        var RELOAD_SUCCESS: String = setPlaceHolders("%prefix &a✔ &7Config & Message files reloaded &aSuccessfully")
        var RELOAD_FAILED: String = setPlaceHolders("%prefix &c✖ &7Config & Message files reload &cFailed")

        var CONFIG_VERSION: Int = 1
    }

    private fun  setPlaceHolders(input: String): String {
        return MessageHelper.colorize(input).replace("%prefix", PREFIX)
    }

    override fun loadValues() {
        val config = getConfig()

        PREFIX = setPlaceHolders(config.getString("Prefix"))
        TRANSFER_SUCCESS = setPlaceHolders(config.getString("Transfer-Success"))
        UPDATE_FOUND = setPlaceHolders(config.getString("Update-Found"))
        ITEM_BLACKLISTED = setPlaceHolders(config.getString("Item-BlackListed"))
        CONTAINER_BLACKLISTED = setPlaceHolders(config.getString("Container-BlackListed"))
        RELOAD_SUCCESS = setPlaceHolders(config.getString("Reload-Success"))
        RELOAD_FAILED = setPlaceHolders(config.getString("Reload-Failed"))

        CONFIG_VERSION = config.getInt("config-version")
    }
}
