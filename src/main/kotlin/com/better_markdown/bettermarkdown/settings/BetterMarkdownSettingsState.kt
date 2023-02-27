package com.better_markdown.bettermarkdown.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.better_markdown.bettermarkdown.SettingsState",
    storages = [
        Storage("BetterMarkdown.xml")
    ]
)
class BetterMarkdownSettingsState : PersistentStateComponent<BetterMarkdownSettingsState.State> {
    private var state = State(headingsRange = BetterMarkdownDefaults.DEFAULT_INT_RANGE_VALUE)

    data class State(
        var headingsRange: IntRange?
    )

    override fun getState(): State? = state

    override fun loadState(state: State) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        public val instance: BetterMarkdownSettingsState
            get() = ApplicationManager
                .getApplication()
                .getService(BetterMarkdownSettingsState::class.java)
    }
}