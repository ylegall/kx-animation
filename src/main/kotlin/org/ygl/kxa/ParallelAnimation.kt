package org.ygl.kxa



class ParallelAnimation(
        private val animations: Iterable<Animatable>
): Animatable {

    constructor(vararg animation: Animatable): this(animation.asIterable())

    private var state: AnimationState = AnimationState.STOPPED
    var onComplete: CompletionCallback? = null

    override val status get() = state

    override fun update(delta: Long) {
        animations.forEach { it.update(delta) }
        if (animations.all { it.isComplete() }) {
            state = AnimationState.COMPLETE
            onComplete?.invoke()
        }
    }

    override fun start() {
        state = AnimationState.RUNNING
        animations.forEach { it.start() }
    }

    override fun stop() {
        state = AnimationState.STOPPED
        animations.forEach { it.stop() }
    }

    override fun reset() {
        state = AnimationState.STOPPED
        animations.forEach { it.reset() }
    }
}

/**
 * utility function for building an unmanaged parallel animation
 */
inline fun parallelAnimation(
        block: CompoundAnimationBuilder.() -> Unit
): ParallelAnimation {
    val animations = CompoundAnimationBuilder().apply(block).animations
    return ParallelAnimation(animations)
}

/**
 * utility function for building an auto-start parallel animation
 */
inline fun animateParallel(
        animationManager: AnimationManager = AnimationManager.DEFAULT,
        block: CompoundAnimationBuilder.() -> Unit
) {
    val builder = CompoundAnimationBuilder()
    val animations = builder.apply(block).animations
    val parallelAnimation = ParallelAnimation(animations)
    parallelAnimation.onComplete = builder.onComplete
    animationManager.addAnimation(parallelAnimation)
}