package org.ygl.kxa

/**
 * interface for base animatable object
 */
interface Animatable {
    fun update(delta: Long)
    fun start()
    fun stop()
    fun reset()
    val status: AnimationState
    fun isComplete() = status == AnimationState.COMPLETE
    fun isRunning() = status == AnimationState.RUNNING
    fun restart() { reset(); start() }
}
