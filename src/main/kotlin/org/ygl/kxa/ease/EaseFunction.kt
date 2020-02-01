package org.ygl.kxa.ease


interface EaseFunction {
    operator fun invoke(t: Double): Double
}