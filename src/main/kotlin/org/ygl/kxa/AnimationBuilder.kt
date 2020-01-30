package org.ygl.kxa

import org.ygl.kxa.ease.Ease
import org.ygl.kxa.ease.FrameProgressProvider
import org.ygl.kxa.ease.Frames
import org.ygl.kxa.ease.frames
import org.ygl.kxa.util.seconds
import java.time.Duration
import kotlin.reflect.KMutableProperty0

/**
 * DSL for building animations
 */
class AnimationBuilder(
        var properties: MutableList<Interval<*>> = mutableListOf(),
        var duration: Duration? = null,
        var frames: Frames? = null,
        var ease: Ease = Ease.NONE,
        var durationDelay: Duration = Duration.ZERO,
        var framesDelay: Frames = 0.frames,
        var onComplete: CompletionCallback? = null
) {
    infix fun <T: Any> KMutableProperty0<T>.from(pair: Pair<T, T>): AnimationBuilder {
        properties.add(Interval(this, pair.first, pair.second))
        return this@AnimationBuilder
    }

    fun <T: Any> from(pair: Pair<KMutableProperty0<T>, T>): AnimationBuilder {
        properties.add(Interval(pair.first, pair.second))
        return this@AnimationBuilder
    }

    fun easedBy(ease: Ease): AnimationBuilder {
        this.ease = ease
        return this
    }

    fun delay(framesDelay: Frames): AnimationBuilder {
        this.framesDelay = framesDelay
        return this
    }

    fun delay(delay: Duration): AnimationBuilder {
        this.durationDelay = delay
        return this
    }

    fun duration(duration: Duration): AnimationBuilder {
        require(frames == null) { "cannot have both frame-based and time-based duration" }
        this.duration = duration
        return this
    }

    fun duration(frames: Frames): AnimationBuilder {
        require(duration == null) { "cannot have both frame-based and time-based duration" }
        this.frames = frames
        return this
    }

    fun onComplete(onComplete: () -> Unit): AnimationBuilder {
        this.onComplete = onComplete
        return this
    }

    fun build(): Animation {
        val progressProvider = when {
            duration != null -> DurationProgressProvider(duration!!, durationDelay)
            frames != null   -> FrameProgressProvider(frames!!, framesDelay)
            else             -> DurationProgressProvider(1.seconds)
        }
        return Animation(properties, progressProvider, ease, onComplete)
    }
}

/**
 *
 */
inline fun animation(block: AnimationBuilder.() -> Unit) = AnimationBuilder().apply {
    block()
}.build()

/**
 *
 */
inline fun animate(
        animationManager: AnimationManager = AnimationManager.DEFAULT,
        block: AnimationBuilder.() -> Unit
) = AnimationBuilder().apply {
    block()
}.build().also { animationManager.addAnimation(it) }
