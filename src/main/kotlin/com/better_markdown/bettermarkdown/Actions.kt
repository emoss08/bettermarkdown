package com.better_markdown.bettermarkdown

import com.better_markdown.bettermarkdown.formatters.TableFormatter
import com.better_markdown.bettermarkdown.generators.TableOfContentsGenerator
import com.better_markdown.bettermarkdown.notifications.BetterMarkdownNotify
import com.better_markdown.bettermarkdown.settings.BetterMarkdownDefaults
import com.better_markdown.bettermarkdown.settings.BetterMarkdownSettingsState
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project

class TableOfContentsAction : AnAction() {

    private val settingsState: BetterMarkdownSettingsState? = BetterMarkdownSettingsState.instance

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        if (!file.extension.equals("md", true)) {
            BetterMarkdownNotify.notifyError(project, "Better Markdown", "Table of contents can only be generated for Markdown files 🤦🏽")
            return
        }

        val maxLevel = settingsState?.state?.maxHeadingLevel ?: BetterMarkdownDefaults.DEFAULT_MAX_LEVEL
        val minLevel = settingsState?.state?.minHeadingLevel ?: BetterMarkdownDefaults.DEFAULT_MIN_LEVEL

        val editor = e.getData(CommonDataKeys.EDITOR) ?: return

        val generatedTableOfContents = TableOfContentsGenerator(minLevel..maxLevel)
            .generateTableOfContents(editor.document.text)

        if (generatedTableOfContents.isEmpty()) {
            BetterMarkdownNotify.notifyWarning(project, "Better Markdown", "No headings found in file 😭")
            return
        }

        val lineStartOffset = editor.caretModel.visualLineStart

        insertTableOfContents(project, editor.document, generatedTableOfContents, lineStartOffset)
        BetterMarkdownNotify.notifyInformation(project, "Better Markdown", "🎉 Table of contents generated!")
    }

    private fun insertTableOfContents(project: Project, document: Document, formattedToc: String, lineStartOffset: Int) {
        WriteCommandAction.runWriteCommandAction(project) {
            document.insertString(lineStartOffset, formattedToc)
        }
    }
}

class FormatTableAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val document = editor.document
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        if (!file.extension.equals("md", true)) {
            Messages.showMessageDialog(
                project,
                "Table can only be formatted for Markdown files",
                "Format Table",
                Messages.getWarningIcon()
            )
            return
        }

        val tableFormatter = TableFormatter()
        val formattedTable = tableFormatter.formatTable(document.text)

        val writeAction = WriteCommandAction.runWriteCommandAction(project) {
            document.setText(formattedTable)
        }

        writeAction.run {
            Messages.showMessageDialog(project, "Table formatted", "Format Table", Messages.getInformationIcon())
        }
    }
}