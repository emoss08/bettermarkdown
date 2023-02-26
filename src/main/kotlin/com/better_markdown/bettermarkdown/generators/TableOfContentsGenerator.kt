package com.better_markdown.bettermarkdown.generators

import java.util.*

class TableOfContentsGenerator {
    private val root = HeadingNode(0, "", "")
    private var currentNode = root

    fun generateTableOfContents(markdown: String): String {
        val headings = parseHeadings(markdown)
        buildHeadingTree(headings)
        return generateTableOfContents(root)
    }

    private fun parseHeadings(markdown: String): List<Heading> {
        val pattern = Regex("#+\\s+(.*)")
        return markdown.lines().mapNotNull { line ->
            pattern.matchEntire(line)?.groupValues?.get(1)?.let { text ->
                Heading(text, line.takeWhile { it == '#' }.length)
            }
        }
    }

    private fun buildHeadingTree(headings: List<Heading>) {
        for (heading in headings) {
            val node = HeadingNode(heading.level, heading.text, generateAnchor(heading.text))
            while (currentNode.level >= heading.level) {
                currentNode = currentNode.parent ?: break
            }
            currentNode.addChild(node)
            currentNode = node
        }
    }

    private fun generateTableOfContents(node: HeadingNode, indentation: Int = 0): String {
        var sb = ""
        if (node.level > 0) {
            sb += " ".repeat(indentation + node.level - 1)
            sb += "- [${node.text}](#${node.anchor})\n"
        }
        for (child in node.children) {
            sb += generateTableOfContents(child, indentation + 1)
        }
        return sb
    }

    private fun generateAnchor(text: String): String {
        return text.lowercase(Locale.getDefault()).replace(Regex("[^a-z0-9]+"), "-")
    }

    private data class Heading(val text: String, val level: Int)

    private class HeadingNode(
        val level: Int,
        val text: String,
        val anchor: String,
        val children: MutableList<HeadingNode> = mutableListOf(),
        var parent: HeadingNode? = null
    ) {
        fun addChild(child: HeadingNode) {
            children.add(child)
            child.parent = this
        }
    }
}
