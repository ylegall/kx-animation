package org.ygl.kxa


/**
 * object representing a no-op animation
 */
object EmptyAnimation: Animatable {
    override fun update(delta: Long) {}
    override fun start() {}
    override fun stop() {}
    override fun reset() {}
    override val status = AnimationState.COMPLETE
}
