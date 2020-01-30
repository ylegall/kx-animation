package org.ygl.kxa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class IntervalTest {

    class TestObject(var intProperty: Int = 0)

    private val testObject = TestObject()

    @Test
    fun `test basic int interval`() {
        val interval = Interval(testObject::intProperty, 2, 10)
        assertThat(interval.update(0.0)).isEqualTo(2)
        assertThat(testObject.intProperty).isEqualTo(2)
        assertThat(interval.update(0.5)).isEqualTo(6)
        assertThat(testObject.intProperty).isEqualTo(6)
        assertThat(interval.update(1.0)).isEqualTo(10)
        assertThat(testObject.intProperty).isEqualTo(10)
    }

    @Test
    fun `test interval with default from param`() {
        val interval = Interval(testObject::intProperty, 10)
        assertThat(interval.update(0.0)).isEqualTo(0)
        assertThat(testObject.intProperty).isEqualTo(0)
        assertThat(interval.update(0.5)).isEqualTo(5)
        assertThat(testObject.intProperty).isEqualTo(5)
        assertThat(interval.update(1.0)).isEqualTo(10)
        assertThat(testObject.intProperty).isEqualTo(10)
    }

    @Test
    fun `test reset interval`() {
        val interval = Interval(testObject::intProperty, 10)
        assertThat(interval.update(1.0)).isEqualTo(10)
        interval.reset()
        assertThat(interval.update(0.0)).isEqualTo(10)
    }
}