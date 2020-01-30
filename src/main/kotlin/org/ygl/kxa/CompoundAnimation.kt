package org.ygl.kxa


/**
 *
 */
class CompoundAnimationBuilder(
        val animations: MutableList<Animatable> = mutableListOf()
) {
    fun animation(animatable: Animatable) {
        animations.add(animatable)
    }

    inline fun animation(block: AnimationBuilder.() -> Unit) {
        val animation = AnimationBuilder().apply(block).build()
        animations.add(animation)
    }

    inline fun sequentialAnimation(block: CompoundAnimationBuilder.() -> Unit) {
        val sequentialAnimations = CompoundAnimationBuilder().apply(block).animations
        animations.add(SequentialAnimation(sequentialAnimations))
    }

    inline fun parallelAnimation(block: CompoundAnimationBuilder.() -> Unit) {
        val parallelAnimations = CompoundAnimationBuilder().apply(block).animations
        animations.add(ParallelAnimation(parallelAnimations))
    }
}