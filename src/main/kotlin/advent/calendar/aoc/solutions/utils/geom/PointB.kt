package advent.calendar.aoc.solutions.utils.geom

import java.math.BigInteger

data class PointB(val x: BigInteger, val y: BigInteger) {
    operator fun plus(p: PointB) = PointB(x + p.x, y + p.y)
    operator fun minus(p: PointB) = PointB(x - p.x, y - p.y)
    operator fun times(a: BigInteger) = PointB(x * a, y * a)
    operator fun times(a: Long) = times(a.toBigInteger())
    operator fun div(a: BigInteger) = PointB(x / a, y / a)
    operator fun div(a: Long) = div(a.toBigInteger())

    val mannSize get() = x + y

    fun by(lambda: Builder.() -> Unit): PointB {
        val builder = Builder(this)
        lambda(builder)
        return builder.build()
    }

    data class Builder(var x: BigInteger, var y: BigInteger) {
        constructor(p: PointB) : this(p.x, p.y)

        fun build() = PointB(x, y)
    }

    companion object {
        val ZERO = PointB(BigInteger.ZERO, BigInteger.ZERO)
    }
}
