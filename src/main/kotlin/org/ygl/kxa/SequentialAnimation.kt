package org.ygl.kxa

/**
 * A sequence of animations
 */
class SequentialAnimation(
        animationIterable: Iterable<Animatable>
): Animatable {

    constructor(vararg animationArray: Animatable): this(
            animationArray.asIterable()
    )

    private var state: AnimationState = AnimationState.STOPPED
    private var currentIndex = 0
    private val animations = animationIterable.toList()

    init {
        require(animations.isNotEmpty()) { "animation sequence must not be empty" }
    }

    override val status get() = state

    override fun update(delta: Long) {
        if (state != AnimationState.RUNNING) return

        animations[currentIndex].let {
            it.update(delta)
            if (it.isComplete()) {
                if (currentIndex < animations.size - 1) {
                    currentIndex++
                    animations[currentIndex].restart()
                } else {
                    state = AnimationState.COMPLETE
                }
            }
        }
    }

    override fun start() {
        if (currentIndex < animations.size) {
            state = AnimationState.RUNNING
            animations[currentIndex].start()
        }
    }

    override fun stop() {
        state = AnimationState.STOPPED
        animations[currentIndex].stop()
    }

    override fun reset() {
        state = AnimationState.STOPPED
        animations.forEach { it.reset() }
        currentIndex = 0
    }
}

/**
 * utility function for building an unmanaged sequential animation
 */
inline fun sequentialAnimation(
        block: CompoundAnimationBuilder.() -> Unit
): SequentialAnimation {
    val animations = CompoundAnimationBuilder().apply(block).animations
    return SequentialAnimation(animations)
}

/**
 * utility function for building an auto-start sequential animation
 */
inline fun animateSequence(
        animationManager: AnimationManager = AnimationManager.DEFAULT,
        block: CompoundAnimationBuilder.() -> Unit
) {
    val animations = CompoundAnimationBuilder().apply(block).animations
    animationManager.addAnimation(SequentialAnimation(animations))
}