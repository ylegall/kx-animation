package org.ygl.kxa.util

import java.time.Duration


val Int.seconds get() = Duration.ofSeconds(this.toLong())

val Long.seconds get() = Duration.ofSeconds(this)

val Int.millis get() = Duration.ofMillis(this.toLong())

val Long.millis get() = Duration.ofMillis(this)
