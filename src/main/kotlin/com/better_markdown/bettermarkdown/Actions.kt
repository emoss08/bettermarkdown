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

class TableOfContentsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val notification = BetterMarkdownNotify()

        if (file.extension.equals("md", true)) {
            val editor = e.getData(CommonDataKeys.EDITOR) ?: return
            val document = editor.document
            val generator = TableOfContentsGenerator()
            val markdown = document.text
            val toc = generator.generateTableOfContents(markdown)

            // Get the start and end offsets of the current line
            val lineStartOffset = editor.caretModel.visualLineStart


            if (toc.isEmpty()) {
                notification.notifyWarning(project, "No headings found in file")
            }

            // TODO: Decide whether add new line before or after the table of contents (or make it configurable)
            val formattedToc = "\n$toc\n"

            val writeAction = WriteCommandAction.runWriteCommandAction(project) {

                // Insert the table of contents at the start of the current line
                document.insertString(lineStartOffset, formattedToc)
            }

            writeAction.run {
                notification.notifyInformation(project, "Table of contents generated!")
            }
        } else {
            notification.notifyError(project, "Table of contents can only be generated for Markdown files")
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