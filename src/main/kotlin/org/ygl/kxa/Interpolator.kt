package org.ygl.kxa

interface Interpolator<T> {
   fun interpolate(from: T, to: T, progress: Double): T
}
