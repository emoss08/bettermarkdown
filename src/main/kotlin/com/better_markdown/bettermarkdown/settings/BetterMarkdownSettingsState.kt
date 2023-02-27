package com.better_markdown.bettermarkdown.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.better_markdown.bettermarkdown.settings.BetterMarkdownSettingsState",
    storages = [
        Storage("BetterMarkdown.xml")
    ]
)
class BetterMarkdownSettingsState : PersistentStateComponent<BetterMarkdownSettingsState> {
    var minHeadingLevel: Int = 1
    var maxHeadingLevel: Int = 6

    override fun getState() = this

    override fun loadState(state: BetterMarkdownSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }


    companion object {
        public val instance: BetterMarkdownSettingsState
            get() = service()
//            get() = ApplicationManager
//                .getApplication()
//                .getService(BetterMarkdownSettingsState::class.java)
    }
}