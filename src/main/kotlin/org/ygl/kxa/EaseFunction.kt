package org.ygl.kxa


interface EaseFunction {
    operator fun invoke(t: Double): Double
}