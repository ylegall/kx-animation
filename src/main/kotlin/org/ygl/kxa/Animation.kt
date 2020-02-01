package org.ygl.kxa

import org.ygl.kxa.ease.Ease
import org.ygl.kxa.ease.EaseFunction
import org.ygl.kxa.util.seconds


class Animation(
        private val properties: Iterable<Interval<*>>,
        private val progressProvider: ProgressProvider = DurationProgressProvider(1.seconds),
        var ease: EaseFunction = Ease.NONE,
        var onComplete: CompletionCallback? = null
): Animatable {

    private var state = AnimationState.STOPPED

    override val status get() = state

    override fun update(delta: Long) {
        if (state == AnimationState.RUNNING) {
            progressProvider.update(delta)
            val progress = progressProvider.progress()
            val easedProgress = ease(progress)
            properties.forEach {
                it.update(easedProgress)
            }
            if (progress >= 1.0) {
                state = AnimationState.COMPLETE
                onComplete?.invoke()
            }
        }
    }

    override fun start() {
        if (state != AnimationState.RUNNING) {
            state = AnimationState.RUNNING
        }
    }

    override fun stop() {
        if (state == AnimationState.RUNNING) {
            state = AnimationState.STOPPED
        }
    }

    override fun reset() {
        state = AnimationState.STOPPED
        progressProvider.reset()
        for (prop in properties) {
            prop.reset()
        }
    }

}
