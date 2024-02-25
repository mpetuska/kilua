@file:Suppress(
    "NO_EXPLICIT_VISIBILITY_IN_API_MODE",
    "NO_EXPLICIT_RETURN_TYPE_IN_API_MODE",
    "EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE",
)
/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// NOTE: THIS FILE IS AUTO-GENERATED, DO NOT EDIT!
// See github.com/kotlin/dukat for details

package web.dom.url

import web.JsAny
import web.JsArray
import web.JsString
import web.dom.mediasource.*
import web.files.*

/**
 * Exposes the JavaScript [URL](https://developer.mozilla.org/en/docs/Web/API/URL) to Kotlin
 */
public open external class URL(url: String, base: String) : JsAny {
    var href: String
    open val origin: String
    var protocol: String
    var username: String
    var password: String
    var host: String
    var hostname: String
    var port: String
    var pathname: String
    var search: String
    open val searchParams: URLSearchParams
    var hash: String

    companion object {
        fun domainToASCII(domain: String): String
        fun domainToUnicode(domain: String): String
        fun createObjectURL(mediaSource: MediaSource): String
        fun createObjectURL(blob: Blob): String
        fun createFor(blob: Blob): String
        fun revokeObjectURL(url: String)
    }
}

/**
 * Exposes the JavaScript [URLSearchParams](https://developer.mozilla.org/en/docs/Web/API/URLSearchParams) to Kotlin
 */
public open external class URLSearchParams(init: JsAny? /* String|URLSearchParams */) : JsAny {
    fun append(name: String, value: String)
    fun delete(name: String)
    fun get(name: String): String?
    fun getAll(name: String): JsArray<JsString>
    fun has(name: String): Boolean
    fun set(name: String, value: String)
}