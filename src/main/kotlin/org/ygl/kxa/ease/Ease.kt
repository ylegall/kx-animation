package org.ygl.kxa.ease


enum class Ease(
        val function: (Double) -> Double,
        private val mode: EaseMode
): EaseFunction {

    NONE(EaseFunctions::linear, EaseMode.IN),

    QUAD_IN    (EaseFunctions::quad, EaseMode.IN),
    QUAD_OUT   (EaseFunctions::quad, EaseMode.OUT),
    QUAD_INOUT (EaseFunctions::quad, EaseMode.INOUT),

    CUBE_IN    (EaseFunctions::cube, EaseMode.IN),
    CUBE_OUT   (EaseFunctions::cube, EaseMode.OUT),
    CUBE_INOUT (EaseFunctions::quad, EaseMode.INOUT),

    QUART_IN    (EaseFunctions::quart, EaseMode.IN),
    QUART_OUT   (EaseFunctions::quart, EaseMode.OUT),
    QUART_INOUT (EaseFunctions::quad, EaseMode.INOUT),

    QUINT_IN    (EaseFunctions::quint, EaseMode.IN),
    QUINT_OUT   (EaseFunctions::quint, EaseMode.OUT),
    QUINT_INOUT (EaseFunctions::quint, EaseMode.INOUT),

    SQRT_IN    (EaseFunctions::sqrt, EaseMode.IN),
    SQRT_OUT   (EaseFunctions::sqrt, EaseMode.OUT),
    SQRT_INOUT (EaseFunctions::sqrt, EaseMode.INOUT),

    EXP_IN    (EaseFunctions::exp, EaseMode.IN),
    EXP_OUT   (EaseFunctions::exp, EaseMode.OUT),
    EXP_INOUT (EaseFunctions::exp, EaseMode.INOUT),

    CIRC_IN    (EaseFunctions::circ, EaseMode.IN),
    CIRC_OUT   (EaseFunctions::circ, EaseMode.OUT),
    CIRC_INOUT (EaseFunctions::circ, EaseMode.INOUT),

    BACK_IN    (EaseFunctions::back, EaseMode.IN),
    BACK_OUT   (EaseFunctions::back, EaseMode.OUT),
    BACK_INOUT (EaseFunctions::back, EaseMode.INOUT),

    SIN_IN    (EaseFunctions::sin, EaseMode.IN),
    SIN_OUT   (EaseFunctions::sin, EaseMode.OUT),
    SIN_INOUT (EaseFunctions::sin, EaseMode.INOUT),

    BOUNCE_IN    (EaseFunctions::bounce, EaseMode.IN),
    BOUNCE_OUT   (EaseFunctions::bounce, EaseMode.OUT),
    BOUNCE_INOUT (EaseFunctions::bounce, EaseMode.INOUT),

    ELASTIC_IN    (EaseFunctions::elastic, EaseMode.IN),
    ELASTIC_OUT   (EaseFunctions::elastic, EaseMode.OUT),
    ELASTIC_INOUT (EaseFunctions::elastic, EaseMode.INOUT)
    ;

    override operator fun invoke(t: Double): Double {
        assert(t in 0.0..1.0)
        return when (mode) {
            EaseMode.IN -> function(t)
            EaseMode.OUT -> 1 - function(1 - t)
            EaseMode.INOUT -> if (t < 0.5) {
                function(2 * t) / 2
            } else {
                1 - function(2 - (2 * t)) / 2
            }
        }
    }
}

