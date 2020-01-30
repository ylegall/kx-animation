package org.ygl.kxa


class ManualUpdateAnimationManager: AnimationManager {

    var autoStart: Boolean = true
    private val animations = mutableListOf<Animatable>()
    var isPaused = false; private set

    override fun addAnimation(animation: Animatable) {
        animations.add(animation)
        if (autoStart) {
            animation.start()
        }
    }

    override fun update(delta: Long) {
        if (isPaused) return

        for (i in animations.size - 1 downTo 0) {
            val animation = animations[i]
            if (animation.isRunning()) {
                animation.update(delta)
                if (animation.isComplete()) {
                    removeAnimationAt(i)
                }
            }
        }
    }

    private fun removeAnimationAt(idx: Int) {
        val lastIndex = animations.size - 1
        if (idx < lastIndex) {
            animations[idx] = animations.last()
        }
        animations.removeAt(lastIndex)
    }

    override fun pause() {
        isPaused = true
    }

    override fun resume() {
        isPaused = false
    }

    override fun shutdown() {}

}