package org.ygl.kxa

import kotlin.reflect.KMutableProperty0


class Interval<T: Any>(
        private val value: KMutableProperty0<T>,
        var from: T,
        var to: T
) {
    constructor(prop: KMutableProperty0<T>, to: T): this(prop, prop.get(), to)

    fun update(progress: Double): T {
        value.set(Interpolators.interpolate(from, to, progress))
        return value.get()
    }

    fun reset() {
        value.set(from)
    }
}
