package org.ygl.kxa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ygl.kxa.ease.frames

internal class ParallelAnimationTest {

    private data class Coordinate(
            var x: Int,
            var y: Int
    )

    private val coordinate = Coordinate(0, 0)

    private val animation1: Animatable = animation {
        from(coordinate::x to 8)
        duration(2.frames)
    }

    private val animation2: Animatable = animation {
        from(coordinate::y to 12)
        duration(2.frames)
    }

    private val animation = ParallelAnimation(animation1, animation2)

    @Test
    fun `test parallel animation`() {
        assertThat(animation1.isRunning()).isFalse()
        assertThat(animation2.isRunning()).isFalse()
        assertThat(animation.isRunning()).isFalse()

        animation.start()
        animation.update(0L)

        assertThat(animation1.isRunning()).isTrue()
        assertThat(animation2.isRunning()).isTrue()
        assertThat(animation.isRunning()).isTrue()

        assertThat(coordinate.x).isEqualTo(4)
        assertThat(coordinate.y).isEqualTo(6)

        animation.update(0L)

        assertThat(animation1.isComplete()).isTrue()
        assertThat(animation2.isComplete()).isTrue()
        assertThat(animation.isComplete()).isTrue()
        assertThat(coordinate.x).isEqualTo(8)
        assertThat(coordinate.y).isEqualTo(12)

        animation.restart()
        assertThat(animation1.isComplete()).isFalse()
        assertThat(animation2.isComplete()).isFalse()
        assertThat(animation.isComplete()).isFalse()
    }

}