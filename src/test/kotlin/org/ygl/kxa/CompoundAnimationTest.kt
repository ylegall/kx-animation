package org.ygl.kxa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ygl.kxa.ease.frames


internal class CompoundAnimationTest {

    private val animationManager = ManualUpdateAnimationManager()

    private data class Attributes(
            var a: Int = 0,
            var b: Int = 0,
            var c: Int = 0,
            var d: Int = 0,
            var e: Int = 0
    )

    private val attributes = Attributes()

    private val compoundAnimation = parallelAnimation {
        sequentialAnimation {
            animation {
                from(attributes::a to 6)
                duration(3.frames)
            }
            animation {
                from(attributes::b to 8)
                duration(2.frames)
            }
        }
        animation {
            from(attributes::c to 8)
            duration(2.frames)
        }
        parallelAnimation {
            animation {
                from(attributes::d to 6)
                duration(2.frames)
            }
            animation {
                from(attributes::e to 8)
                duration(4.frames)
            }
        }
    }

    @Test
    fun `test compound animation`() {
        animationManager.addAnimation(compoundAnimation)

        assertThat(attributes).isEqualTo(Attributes(0, 0, 0, 0, 0))
        animationManager.update(0L)
        assertThat(attributes).isEqualTo(Attributes(2, 0, 4, 3, 2))
        animationManager.update(0L)
        assertThat(attributes).isEqualTo(Attributes(4, 0, 8, 6, 4))
        animationManager.update(0L)
        assertThat(attributes).isEqualTo(Attributes(6, 0, 8, 6, 6))
        animationManager.update(0L)
        assertThat(attributes).isEqualTo(Attributes(6, 4, 8, 6, 8))
        animationManager.update(0L)
        assertThat(attributes).isEqualTo(Attributes(6, 8, 8, 6, 8))
    }
}