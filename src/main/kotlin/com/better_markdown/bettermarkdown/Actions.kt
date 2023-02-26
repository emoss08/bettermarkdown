package com.better_markdown.bettermarkdown

import com.better_markdown.bettermarkdown.formatters.TableFormatter
import com.better_markdown.bettermarkdown.generators.TableOfContentsGenerator
import com.better_markdown.bettermarkdown.notifications.BetterMarkdownNotify
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project

class TableOfContentsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        if (file.extension.equals("md", true)) {
            val editor = e.getData(CommonDataKeys.EDITOR) ?: return
            val document = editor.document
            val generator = TableOfContentsGenerator()
            val markdown = document.text
            val toc = generator.generateTableOfContents(markdown)

            if (toc.isEmpty()) {
                notifyWarning(project, "No headings found in file")
                return
            }

            val formattedToc = "\n$toc\n"
            val lineStartOffset = editor.caretModel.visualLineStart

            insertToc(project, document, formattedToc, lineStartOffset)
            notifyInformation(project, "Table of contents generated!")
        } else {
            notifyError(project, "Better Markdown", "Table of contents can only be generated for Markdown files")
        }
    }

    private fun insertToc(project: Project, document: Document, formattedToc: String, lineStartOffset: Int) {
        WriteCommandAction.runWriteCommandAction(project) {
            document.insertString(lineStartOffset, formattedToc)
        }
    }

    private fun notifyWarning(project: Project, message: String) {
        BetterMarkdownNotify().notifyWarning(project, message)
    }

    private fun notifyError(project: Project, title: String, message: String) {
        BetterMarkdownNotify().notifyError(project, title, message)
    }

    private fun notifyInformation(project: Project, message: String) {
        BetterMarkdownNotify().notifyInformation(project, message)
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