package org.ygl.kxa

import java.time.Duration


class DurationProgressProvider(
        duration: Duration,
        delay: Duration = Duration.ZERO
): ProgressProvider {

    private val totalMillis = duration.toMillis().toDouble()
    private val delayMillis = delay.toMillis()
    private var elapsed: Long = 0

    init {
        require(totalMillis > 0) { "duration must be > 0" }
        require(delayMillis >= 0) { "delay must be >= 0" }
    }

    override fun reset() {
        elapsed = 0
    }

    override fun update(dt: Long) {
        elapsed += dt
    }

    override fun progress() = ((elapsed - delayMillis) / totalMillis).coerceIn(0.0, 1.0)
}

fun Duration.delay(delay: Duration) = DurationProgressProvider(this, delay)
