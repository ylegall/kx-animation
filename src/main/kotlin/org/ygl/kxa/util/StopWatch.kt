package org.ygl.kxa.util

private const val NANOS_PER_MILLISECOND = 1000000L

class StopWatch(
        private var lastTimestamp: Long = 0L
) {
    private var extraNanos = 0L

    fun elapsedNanos(): Long {
        val newTimestamp = System.nanoTime()
        return if (lastTimestamp == 0L) {
            lastTimestamp = newTimestamp
            0
        } else {
            (newTimestamp - lastTimestamp).also {
                lastTimestamp = newTimestamp
            }
        }
    }

    fun elapsedMillis(): Long {
        val elapsedNanos = elapsedNanos()
        val totalNanos = elapsedNanos + extraNanos
        return if (totalNanos / NANOS_PER_MILLISECOND == 0L) {
            extraNanos += elapsedNanos
            0
        } else {
            extraNanos = totalNanos % NANOS_PER_MILLISECOND
            totalNanos / NANOS_PER_MILLISECOND
        }
    }

    fun reset() {
        lastTimestamp = 0L
    }
}