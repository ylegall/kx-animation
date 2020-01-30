package org.ygl.kxa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.ygl.kxa.ease.frames


internal class SequentialAnimationTest {

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

    @Test
    fun `test that a zero-length sequence throws`() {
        assertThrows<IllegalArgumentException> {
            SequentialAnimation()
        }
    }

    @Test
    fun `test sequential animation`() {
        val animation = SequentialAnimation(animation1, animation2)
        animation.start()

        animation.update(0L)
        assertThat(coordinate.x).isEqualTo(4)
        assertThat(coordinate.y).isEqualTo(0)

        animation.update(0L)
        assertThat(coordinate.x).isEqualTo(8)
        assertThat(coordinate.y).isEqualTo(0)

        animation.update(0L)
        assertThat(coordinate.x).isEqualTo(8)
        assertThat(coordinate.y).isEqualTo(6)

        animation.update(0L)
        assertThat(coordinate.x).isEqualTo(8)
        assertThat(coordinate.y).isEqualTo(12)

        assertThat(animation.isComplete())
    }

}