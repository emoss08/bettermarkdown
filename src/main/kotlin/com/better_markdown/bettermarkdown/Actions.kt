package com.better_markdown.bettermarkdown

import com.better_markdown.bettermarkdown.generators.TableOfContentsGenerator
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages

import com.intellij.openapi.command.WriteCommandAction

class TableOfContentsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val document = editor.document
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        if (!file.extension.equals("md", true)) {
            Messages.showMessageDialog(project, "Table of contents can only be generated for Markdown files", "Table of Contents", Messages.getWarningIcon())
            return
        }

        val generator = TableOfContentsGenerator()
        val toc = generator.generateTableOfContents(document.text)

        val writeAction = WriteCommandAction.runWriteCommandAction(project) {
            document.insertString(0, toc)
        }
//        val writeAction = object : WriteCommandAction.Simple<Any>(project, "Generate Table of Contents") {
//            override fun run() {
//                document.insertString(0, toc)
//            }
//        }

        writeAction.run {
            Messages.showMessageDialog(project, "Table of contents generated and inserted at the top of the file", "Table of Contents", Messages.getInformationIcon())
        }

    }
}