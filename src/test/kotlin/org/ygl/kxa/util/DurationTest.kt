package org.ygl.kxa.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Duration


internal class DurationTest {

    @Test
    fun `test duration dsl`() {
        val duration = 5.seconds
        assertThat(duration).isInstanceOf(Duration::class.java)
        assertThat(duration.seconds).isEqualTo(5)

        val longDuration = 8L.seconds
        assertThat(longDuration).isInstanceOf(Duration::class.java)
        assertThat(longDuration.seconds).isEqualTo(8)

        val durationMillis = 5.millis
        assertThat(durationMillis).isInstanceOf(Duration::class.java)
        assertThat(durationMillis.toMillis()).isEqualTo(5)

        val longDurationMillis = 8L.millis
        assertThat(longDurationMillis).isInstanceOf(Duration::class.java)
        assertThat(longDurationMillis.toMillis()).isEqualTo(8)
    }
}
