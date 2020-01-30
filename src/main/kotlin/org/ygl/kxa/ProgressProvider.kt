package org.ygl.kxa


interface ProgressProvider {
    fun reset()
    fun update(dt: Long)
    fun progress(): Double
}
