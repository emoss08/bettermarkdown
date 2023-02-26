package com.better_markdown.bettermarkdown.formatters

class TableFormatter {
    fun formatTable(input: String): String {
        val rows = input.trim().split('\n').map { it.trim() }
        val columnCount = rows[0].count { it == '|' } + 1
        val columnWidths = IntArray(columnCount) { 0 }
        val cells = mutableListOf<List<String>>()

        for (row in rows) {
            val cellValues = row.split('|').map { it.trim() }
            println("cellValues: ${cellValues.size}")
            println("columnCount: $columnCount")
            if (cellValues.size != columnCount) {
                throw IllegalArgumentException("Invalid table format")
            }
            cells.add(cellValues)
            for ((i, value) in cellValues.withIndex()) {
                columnWidths[i] = maxOf(columnWidths[i], value.length)
            }
        }

        val sb = StringBuilder()
        for ((i, row) in cells.withIndex()) {
            if (i == 1) {
                sb.append("|")
                for ((j, width) in columnWidths.withIndex()) {
                    val alignment = when {
                        rows[0][j] == ':' && rows[0][rows[0].length - j - 2] == ':' -> ":-:"
                        rows[0][j] == ':' -> ":-"
                        rows[0][rows[0].length - j - 2] == ':' -> "-:"
                        else -> "--"
                    }
                    sb.append(" $alignment ".padEnd(width, '-'))
                    sb.append("|")
                }
                sb.append("\n")
            }
            sb.append("|")
            for ((j, value) in row.withIndex()) {
                sb.append(" ")
                sb.append(value.padEnd(columnWidths[j]))
                sb.append(" |")
            }
            sb.append("\n")
        }

        return sb.toString()
    }

}