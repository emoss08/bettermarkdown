package com.better_markdown.bettermarkdown.generators

import java.util.*

class TableOfContentsGenerator {
    fun generateTableOfContents(markdown: String): String {
        val pattern = Regex("(?m)^#+\\s+(.*)\$")
        val matches = pattern.findAll(markdown)
        val toc = StringBuilder()

        for (match in matches) {
            val heading = match.groupValues[1]
            val level = match.value.count { it == '#' }
            val indent = "  ".repeat(level - 1)
            toc.append(
                "$indent- [$heading](#${
                    heading.lowercase(Locale.getDefault()).replace("\\s".toRegex(), "-")
                })\n"
            )
        }

        return toc.toString()
    }
}