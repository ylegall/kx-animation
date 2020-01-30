package org.ygl.kxa



class ParallelAnimation(
        private val animations: Iterable<Animatable>
): Animatable {

    constructor(vararg animation: Animatable): this(animation.asIterable())

    private var state: AnimationState = AnimationState.STOPPED
    var onComplete: () -> Unit = {}

    override val status get() = state

    override fun update(delta: Long) {
        animations.forEach { it.update(delta) }
        if (animations.all { it.isComplete() }) {
            state = AnimationState.COMPLETE
            onComplete()
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

//class ParallelAnimationBuilder {
//    val animations = mutableListOf<Animatable>()
//    var completionCallback: () -> Unit = {}
//
//    inline fun animation(block: AnimationBuilder.() -> Unit) {
//        val animation = org.ygl.kxa.animation { block() }
//        animations.add(animation)
//    }
//
//    inline fun parallelAnimation(block: ParallelAnimationBuilder.() -> Unit) {
//        val builder = ParallelAnimationBuilder()
//        builder.block()
//        animations.add(builder.build())
//    }
//
//    inline fun sequentialAnimation(block: SequentialAnimationBuilder.() -> Unit) {
//        val builder = SequentialAnimationBuilder()
//        builder.block()
//        animations.add(builder.build())
//    }
//
//    fun onComplete(onComplete: () -> Unit) {
//        completionCallback = onComplete
//    }
//
//    fun build() = ParallelAnimation(animations).apply { onComplete = completionCallback }
//}
//
///**
// * utility function for building an unmanaged parallel animation
// */
//inline fun parallelAnimation(
//        block: ParallelAnimationBuilder.() -> Unit
//): ParallelAnimation {
//    return ParallelAnimationBuilder().apply {
//        block()
//    }.build()
//}
//
///**
// * utility function for building an auto-start parallel animation
// */
//inline fun animateParallel(
//        animationManager: AnimationManager = AnimationManager.DEFAULT,
//        block: ParallelAnimationBuilder.() -> Unit
//): ParallelAnimation {
//    val animation = ParallelAnimationBuilder().apply {
//        block()
//    }.build()
//    animationManager.addAnimation(animation)
//    return animation
//}

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
    val animations = CompoundAnimationBuilder().apply(block).animations
    animationManager.addAnimation(ParallelAnimation(animations))
}