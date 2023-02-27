package com.better_markdown.bettermarkdown.settings

import com.better_markdown.bettermarkdown.utils.toIntRange
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel

interface IBetterMarkdownSettingsComponent {
    val preferredFocusedComponent: JComponent
    val panel: JPanel
    fun setRange(rangeText: String)
    val rangeText: String
    fun setValidationStatus(isValid: Boolean, message: String?)
}

/**
 * This is the UI holder essentially that implements Java Swing components.
 * The [BetterMarkdownSettingsConfigurable] will interact with this class.
 */
class BetterMarkdownSettingsComponent : IBetterMarkdownSettingsComponent {
    private val rangeField: JBTextField = JBTextField()
    override val preferredFocusedComponent: JComponent = rangeField

    override val panel: JPanel = FormBuilder.createFormBuilder().addLabeledComponent(
        JBLabel("Table of Content range (e.g. ${BetterMarkdownDefaults.DEFAULT_INT_RANGE_VALUE})"), rangeField, 1, false
    ).addComponentFillVertically(JPanel(), 0).panel

    // hello shuttle
    override fun setRange(rangeText: String) {
        rangeText.toIntRange()?.let {
            rangeField.text = rangeText
        }
    }

    override val rangeText: String
        get() = rangeField.text

    override fun setValidationStatus(isValid: Boolean, message: String?) {
        val border = if (!isValid) {
            BorderFactory.createLineBorder(JBColor.RED)
        } else {
            null
        }
        rangeField.border = border
    }
}