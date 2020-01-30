package org.ygl.kxa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.ygl.kxa.util.seconds
import kotlin.math.abs

internal class DurationProgressProviderTest {

    private val tolerance = 0.0001

    @Test
    fun `test that duration must be positive`() {
        assertThrows<IllegalArgumentException> { DurationProgressProvider((-4).seconds) }
    }

    @Test
    fun `test that delay must be positive`() {
        assertThrows<IllegalArgumentException> { DurationProgressProvider(4.seconds, delay = (-4).seconds) }
    }

    @Test
    fun `test duration based progress provider with zero delay`() {
        val totalDuration = 10.seconds
        val progressProvider = DurationProgressProvider(totalDuration)
        val delta = totalDuration.toMillis() / 10
        var progress = 0.0

        repeat(10) {
            progressProvider.assertProgress(progress)
            progressProvider.update(delta)
            progress += 0.1
        }

        with (progressProvider) {
            assertProgress(1.0)
            reset()
            assertProgress(0.0)
        }
    }

    @Test
    fun `test duration based progress provider with delay`() {
        val totalDuration = 10.seconds
        val delay = 3.seconds
        val progressProvider = DurationProgressProvider(totalDuration, delay)
        progressProvider.update(3.seconds.toMillis())
        progressProvider.assertProgress(0.0)
        progressProvider.update(5.seconds.toMillis())
        progressProvider.assertProgress(0.5)
    }

    private fun DurationProgressProvider.assertProgress(progress: Double) {
        assertThat(abs(progress() - progress)).isLessThan(tolerance)
    }
}