package org.ygl.kxa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.math.abs


class FrameProgressProviderTest {

    private val tolerance = 0.0001

    @Test
    fun `test create frames from Int`() {
        assertThat(5.frames).isEqualTo(Frames(5L))
    }

    @Test
    fun `test create frames from Long`() {
        assertThat(3.frames.count).isEqualTo(Frames(3L).count)
    }

    @Test
    fun `test that frames must be positive`() {
        assertThrows<IllegalArgumentException> { FrameProgressProvider(Frames(-4)) }
    }

    @Test
    fun `test that delay must be positive`() {
        assertThrows<IllegalArgumentException> { FrameProgressProvider(Frames(4), delay = Frames(-4)) }
    }

    @Test
    fun `test frame progress provider with no delay`() {
        val frameDuration = FrameProgressProvider(10.frames)
        var progress = 0.0
        repeat(10) {
            frameDuration.assertProgress(progress)
            progress += 0.1
            frameDuration.update(0L)
        }
        with (frameDuration) {
            assertProgress(1.0)
            reset()
            assertProgress(0.0)
        }
    }

    @Test
    fun `test frame progress provider delay`() {
        val frames = 10.frames
        val delay = 3.frames
        val progressProvider = FrameProgressProvider(frames, delay)
        repeat(2) { progressProvider.update(0L) }
        progressProvider.assertProgress(0.0)
        repeat(6) { progressProvider.update(0L) }
        progressProvider.assertProgress(0.5)
    }

    private fun FrameProgressProvider.assertProgress(progress: Double) {
        assertThat(abs(progress() - progress)).isLessThan(tolerance)
    }
}
