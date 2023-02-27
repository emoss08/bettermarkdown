package com.better_markdown.bettermarkdown.utils

fun String.toIntRange(): IntRange? {
    val split = this.split("..")

    if (split.size != 2) {
        println("Invalid Range")
        return null
    }

    val (a, b) = split

    return try {
        a.toInt()..b.toInt()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        println("Values '$a' and/or '$b' are not integers")
        null
    }
}