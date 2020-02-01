package org.ygl.kxa

import kotlin.math.min
import kotlin.reflect.KClass

object Interpolators {

    private val customInterpolators = HashMap<Class<*>, Interpolator<*>>()

    fun <T: Any> registerInterpolator(type: Class<T>, interpolator: Interpolator<T>) {
        customInterpolators[type] = interpolator
    }

    fun <T: Any> registerInterpolator(type: KClass<T>, interpolator: Interpolator<T>) {
        customInterpolators[type.java] = interpolator
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: Any> interpolate(from: T, to: T, t: Double): T {
        @Suppress("IMPLICIT_CAST_TO_ANY")
        return when (from) {
            is Double -> interpolate(from, to as Double, t)
            is Float  -> interpolate(from, to as Float, t)
            is Int    -> interpolate(from, to as Int, t)
            is Long   -> interpolate(from, to as Long, t)
            else      -> {
                val customInterpolator = (customInterpolators[from.javaClass] as? Interpolator<T>)
                checkNotNull(customInterpolator) { "no interpolator registered for type ${from::class.java}" }
                customInterpolator.interpolate(from, to, t)
            }
        } as T
    }

    fun interpolate(from: Double, to: Double, t: Double) = from + (to - from) * t
    fun interpolate(from: Float, to: Float, t: Double) = (from + (to - from) * t).toFloat()
    fun interpolate(from: Int, to: Int, t: Double) = (from + (to - from) * t).toInt()
    fun interpolate(from: Long, to: Long, t: Double) = (from + (to - from) * t).toLong()

    fun <A: Any, B: Any> interpolate(from: Pair<A, B>, to: Pair<A, B>, t: Double) = Pair(
            interpolate(from.first, to.first, t),
            interpolate(from.second, to.second, t)
    )

    fun <A: Any, B: Any, C: Any> interpolate(from: Triple<A, B, C>, to: Triple<A, B, C>, t: Double) = Triple(
            interpolate(from.first, to.first, t),
            interpolate(from.second, to.second, t),
            interpolate(from.third, to.third, t)
    )

    fun <T: Any> interpolate(from: Iterable<T>, to: Iterable<T>, t: Double): List<T> {
        return from.zip(to).map { interpolate(it.first, it.second, t) }
    }

    fun interpolate(from: Array<Any>, to: Array<Any>, t: Double): Array<Any> {
        return Array(min(from.size, to.size)) { idx -> interpolate(from[idx], to[idx], t) }
    }
}

