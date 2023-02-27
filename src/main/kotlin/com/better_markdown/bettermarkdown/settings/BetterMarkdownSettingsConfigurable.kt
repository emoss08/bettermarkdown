package com.better_markdown.bettermarkdown.settings

import com.better_markdown.bettermarkdown.utils.component1
import com.better_markdown.bettermarkdown.utils.component2
import com.better_markdown.bettermarkdown.utils.toIntRange
import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent

/**
 * The IntelliJ platform interacts with this class to get UI elements.
 * This class implements UI from [BetterMarkdownSettingsComponent]
 */
class BetterMarkdownSettingsConfigurable : SearchableConfigurable {
    private var settingsComponent: BetterMarkdownSettingsComponent? = null

    override fun createComponent(): JComponent? {
        settingsComponent = BetterMarkdownSettingsComponent()

        return settingsComponent?.panel
    }

    /**
     * If we don't have state or a settings component, return true
     */
    override fun isModified(): Boolean {
        val state = BetterMarkdownSettingsState.instance.state
        val settingsComponent = settingsComponent ?: return true

        val uiRange: IntRange = settingsComponent.rangeText.toIntRange() ?: return true

        val (uiMinLevel, uiMaxLevel) = uiRange

        val minLevelChanged = uiMinLevel != state.minHeadingLevel
        val maxLevelChanged = uiMaxLevel != state.maxHeadingLevel

        return minLevelChanged || maxLevelChanged
    }

    /**
     * If we don't have a settings component or a state, we don't apply anything
     */
    override fun apply() {
        val state = BetterMarkdownSettingsState.instance.state
        val settingsComponent = settingsComponent ?: return

        val range: IntRange = settingsComponent.rangeText.toIntRange() ?: BetterMarkdownDefaults.DEFAULT_INT_RANGE_VALUE

        val (minLevel, maxLevel) = range

        state.minHeadingLevel = minLevel
        state.maxHeadingLevel = maxLevel
    }

    override fun reset() {
        val state = BetterMarkdownSettingsState.instance.state
        val settingsComponent = settingsComponent ?: return

        settingsComponent.setRange(state.rangeToString())
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }

    override fun getDisplayName(): String = "BetterMarkdown Settings"

    override fun getId(): String =
        "com.better_markdown.bettermarkdown.settings.BetterMarkdownSettingsConfigurable"
}


