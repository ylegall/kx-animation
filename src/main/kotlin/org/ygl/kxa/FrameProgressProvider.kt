package org.ygl.kxa


class FrameProgressProvider(
        totalFrames: Frames,
        private val delay: Frames = Frames(0)
): ProgressProvider {

    init {
        require(totalFrames.count > 0) { "total frame count must be > 0" }
        require(delay.count >= 0)      { "delay frames must be >= 0" }
    }

    private val totalFrames = totalFrames.count.toDouble()
    private var currentFrame = 0

    override fun reset() {
        currentFrame = 0
    }

    override fun update(dt: Long) {
        currentFrame++
    }

    override fun progress() = ((currentFrame - delay.count) / totalFrames).coerceIn(0.0, 1.0)
}

inline class Frames(val count: Long)

val Int.frames: Frames get()  = Frames(this.toLong())
val Long.frames: Frames get() = Frames(this)