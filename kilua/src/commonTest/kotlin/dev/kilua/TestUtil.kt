/*
 * Copyright (c) 2023 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kilua

import dev.kilua.compose.Root
import dev.kilua.externals.Object
import dev.kilua.utils.cast
import dev.kilua.utils.isDom
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.promise
import kotlinx.dom.clear
import kotlin.js.Promise
import kotlin.test.DefaultAsserter.assertTrue

val testScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

interface TestSpec {

    fun beforeTest()

    fun afterTest()

    fun run(code: () -> Unit) {
        beforeTest()
        code()
        afterTest()
    }

    fun runAsync(code: suspend () -> Unit): Promise<Object> {
        beforeTest()
        return testScope.promise {
            code()
        }.finally {
            afterTest()
        }.cast()
    }
}

interface SimpleSpec : TestSpec {

    override fun beforeTest() {
    }

    override fun afterTest() {
    }

}

interface DomSpec : TestSpec {

    fun getTestId() = "test"

    fun runWhenDomAvailable(code: () -> Unit) {
        beforeTest()
        if (isDom) {
            run(code)
        }
        afterTest()
    }

    fun runWhenDomAvailableAsync(code: suspend () -> Unit): Promise<Object> {
        beforeTest()
        return testScope.promise {
            if (isDom) code()
        }.finally {
            afterTest()
        }.cast()
    }

    override fun beforeTest() {
        if (isDom) {
            val fixture = "<div style=\"display: none\" id=\"pretest\">" +
                    "<div id=\"${getTestId()}\"></div></div>"
            document.body?.insertAdjacentHTML("afterbegin", fixture)
        }
    }

    override fun afterTest() {
        if (isDom) {
            val div = document.getElementById("pretest")
            div?.clear()
            val modalBackdrop = document.getElementById(".modal-backdrop")
            modalBackdrop?.remove()
        }
        Root.disposeAllRoots()
    }

    fun assertEqualsHtml(
        expected: String?,
        actual: String?,
        message: String? = null,
        normalizeHtml: Boolean = true,
    ) {
        val normalizedExpected = if (normalizeHtml) normalizeHtml(expected) else expected
        val normalizedActual = if (normalizeHtml) normalizeHtml(actual) else actual
        assertTrue(
            {
                (if (message == null) "" else "$message. ") +
                        "Expected <$normalizedExpected>, actual <$normalizedActual>."
            },
            htmlDifferEquals(expected, actual)
        )
    }

    companion object {

        private val htmlDiffer = HtmlDiffer("bem")

        fun htmlDifferEquals(expected: String?, actual: String?): Boolean {
            return if (expected.isNullOrBlank() || actual.isNullOrBlank()) {
                false
            } else {
                htmlDiffer.isEqual(expected, actual)
            }
        }
    }
}

/**
 * Format an HTML string in a standardized manner, with one HTML element per line.
 * Removes auto-generated IDs from the HTML.
 * This helps with highlighting HTML differences in test assertions.
 */
fun normalizeHtml(raw: String): String {
    return raw
        .replace("<", "\n<")
        .replace(">", ">\n")
        .split("\n")
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .joinToString("\n")
}

/** @see [normalizeHtml] */
fun normalizeHtml(raw: String?): String? {
    return if (raw == null) null else normalizeHtml(raw)
}
