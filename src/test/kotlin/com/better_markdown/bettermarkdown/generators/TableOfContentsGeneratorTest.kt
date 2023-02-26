package com.better_markdown.bettermarkdown.generators

import org.junit.jupiter.api.Assertions.*

import org.junit.Test

class TableOfContentsGeneratorTest {


    @Test
    fun `produces the right output`() {

        val subject = TableOfContentsGenerator()

        val input = """
            # Heading 1
            ## Heading 1.1
            ### Heading 1.1.1
            ## Heading 1.2
            # Heading 2
            ## Heading 2.1
            ### Heading 2.1.1
            #### Heading
            # Heading 3
            """.trimIndent()

        val result = subject.generateTableOfContents(input)

        val expected = """
            - [Heading 1](#heading-1)
                - [Heading 1.1](#heading-1-1)
                    - [Heading 1.1.1](#heading-1-1-1)
                - [Heading 1.2](#heading-1-2)
            - [Heading 2](#heading-2)
                - [Heading 2.1](#heading-2-1)
                    - [Heading 2.1.1](#heading-2-1-1)
                        - [Heading](#heading)
            - [Heading 3](#heading-3)
            
            """.trimIndent()

        assertEquals(expected, result)

    }
}