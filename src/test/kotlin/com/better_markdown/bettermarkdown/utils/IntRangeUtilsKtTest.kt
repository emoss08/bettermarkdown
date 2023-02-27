package com.better_markdown.bettermarkdown.utils

import com.better_markdown.bettermarkdown.settings.BetterMarkdownDefaults.DEFAULT_INT_RANGE_VALUE
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class IntRangeUtilsKtTest {
    @Test
    fun `can parse an int range from string`() {
        val result = "1..6".toIntRange()

        assertEquals(result!!.first, 1)
        assertEquals(result!!.last, 6)
    }

    @Test
    fun `returns null when invalid range provided without range syntax`() {
        assertNull("hello shuttle".toIntRange())
    }

    @Test
    fun `returns null when invalid range provided with range syntax`() {
        assertNull("hello..shuttle".toIntRange())
    }

    @Test
    fun `can toString() an IntRange`() {
        assertEquals("1..6", DEFAULT_INT_RANGE_VALUE.toString())
    }
}