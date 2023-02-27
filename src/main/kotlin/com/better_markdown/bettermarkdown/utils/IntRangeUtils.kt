package com.better_markdown.bettermarkdown.utils

fun String.toIntRange(): IntRange? {
    val split = this.split("..")

    if (split.size != 2) return null

    val (a, b) = split

    return try {
        a.toInt()..b.toInt()
    } catch (e: NumberFormatException) {
        null
    }
}

public operator fun IntRange.component1(): Int = this.first

public operator fun IntRange.component2(): Int = this.last