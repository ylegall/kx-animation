package org.ygl.kxa.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ygl.kxa.util.StopWatch
import java.lang.Thread.sleep


internal class StopWatchTest {

    @Test
    fun `test stopwatch starts and resets to zero`() {
        val stopWatch = StopWatch()
        assertThat(stopWatch.elapsedMillis()).isEqualTo(0)
        sleep(500)
        assertThat(stopWatch.elapsedMillis()).isGreaterThan(0)
        stopWatch.reset()
        assertThat(stopWatch.elapsedMillis()).isEqualTo(0)
    }

}