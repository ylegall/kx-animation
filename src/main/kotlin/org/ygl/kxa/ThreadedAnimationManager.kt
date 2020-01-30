package org.ygl.kxa

import org.ygl.kxa.util.StopWatch
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock


/**
 * Animation Manager using a dedicated animation thread
 */
class ThreadedAnimationManager(
        var autoStart: Boolean = true
): AnimationManager {
    private val stopWatch: StopWatch = StopWatch()
    private val animations = mutableListOf<Animatable>()
    private val lock = ReentrantLock()
    private val animationsNotEmpty = lock.newCondition()
    private val animationsUnpaused = lock.newCondition()
    var isPaused = false; private set
    private var running = true

    init {
        thread(start = true, isDaemon = true) { start() }
    }

    private fun start() {
        stopWatch.elapsedMillis()
        while (running) {
            val delta = stopWatch.elapsedMillis()
            when {
                isPaused -> lock.withLock {
                    animationsUnpaused.await()
                    stopWatch.elapsedMillis()
                }
                animations.isEmpty() -> lock.withLock {
                    animationsNotEmpty.await()
                    stopWatch.elapsedMillis()
                }
                else -> {
                    update(delta)
                }
            }
        }
    }

    override fun addAnimation(animation: Animatable) {
        animations.add(animation)
        lock.withLock { animationsNotEmpty.signalAll() }
        if (autoStart) {
            animation.start()
        }
    }

    override fun update(delta: Long) {
        if (animations.isEmpty() or isPaused) return

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
        lock.withLock { animationsUnpaused.signalAll() }
    }

    override fun shutdown() {
        running = false
    }
}