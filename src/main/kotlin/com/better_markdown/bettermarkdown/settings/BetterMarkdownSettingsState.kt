package com.better_markdown.bettermarkdown.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.better_markdown.bettermarkdown.settings.BetterMarkdownSettingsState",
    storages = [
        Storage("BetterMarkdown.xml")
    ]
)
class BetterMarkdownSettingsState : PersistentStateComponent<BetterMarkdownSettingsState> {
    var minHeadingLevel: Int = BetterMarkdownDefaults.DEFAULT_MIN_LEVEL
    var maxHeadingLevel: Int = BetterMarkdownDefaults.DEFAULT_MAX_LEVEL

    override fun getState() = this

    override fun loadState(state: BetterMarkdownSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    fun rangeToString(): String = "${minHeadingLevel}..${maxHeadingLevel}"

    companion object {
        public val instance: BetterMarkdownSettingsState
            get() = service()
    }
}