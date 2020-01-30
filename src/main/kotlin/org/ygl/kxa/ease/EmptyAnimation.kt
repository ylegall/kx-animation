package org.ygl.kxa.ease

import org.ygl.kxa.Animatable
import org.ygl.kxa.AnimationState


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
