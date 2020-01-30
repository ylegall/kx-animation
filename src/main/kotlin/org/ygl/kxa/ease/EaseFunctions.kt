package org.ygl.kxa.ease

import kotlin.math.cos
import kotlin.math.pow


object EaseFunctions {

    fun linear(t: Double) = t

    fun quad(t: Double)   = t * t

    fun cube(t: Double)   = t * t * t

    fun sqrt(t: Double)   = kotlin.math.sqrt(t)

    fun quart(t: Double)  = (t * t).let { it * it}

    fun quint(t: Double)  = (t * t).let { it * it * t}

    fun sin(t: Double)    = 1 - cos(t * (Math.PI/2))

    fun exp(t: Double)    = 2.0.pow(10 * (t - 1))

    fun circ(t: Double)   = 1 - kotlin.math.sqrt(1 - (t * t))

    fun back(t: Double)   = (t * t) * ((2.70158 * t) - 1.70158)

    fun elastic(t: Double): Double {
        if (t == 0.0) return 0.0
        if (t == 1.0) return 1.0
        val p = 0.3
        val s = p / 4.0
        val exp = exp(t)
        return -exp * kotlin.math.sin((t - 1 - s) * (2 * Math.PI) / p)
    }

    fun bounce(t: Double): Double {
        return when {
            t < 1/2.75 -> 7.5625 * t * t
            t < 2/2.75 -> {
                val t2 = t - 1.5/2.75
                7.5625 * (t - 1.5/2.75) * t2 + .75
            }
            t < 2.5/2.75 -> {
                val t2 = t - 2.25/2.75
                7.5625 * (t - 2.25/2.75) * t2 + .9375
            }
            else -> {
                val t2 = t - 2.625f/2.75f
                7.5625 * (t - 2.625f/2.75f) * t2 + .984375
            }
        }
    }
}