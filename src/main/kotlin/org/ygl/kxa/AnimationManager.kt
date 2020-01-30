package org.ygl.kxa


/**
 * interface for animation manager
 */
interface AnimationManager {

    fun addAnimation(animation: Animatable)

    fun update(delta: Long)

    fun pause()

    fun resume()

    fun shutdown()

    companion object {
        val DEFAULT: ThreadedAnimationManager by lazy { ThreadedAnimationManager() }
    }
}