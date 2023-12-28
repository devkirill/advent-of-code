package advent.calendar.aoc.solutions.utils.geom

import java.math.BigDecimal

data class Vector3bd(val x: BigDecimal, val y: BigDecimal, val z: BigDecimal) {
    constructor(x: Double, y: Double, z: Double) : this(x.toBigDecimal(), y.toBigDecimal(), z.toBigDecimal())
    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())

    operator fun plus(v: Vector3bd) = Vector3bd(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3bd) = Vector3bd(x - v.x, y - v.y, z - v.z)
    operator fun times(t: Double) = Vector3bd(x * t.toBigDecimal(), y * t.toBigDecimal(), z * t.toBigDecimal())
    operator fun div(t: Double) = Vector3bd(x / t.toBigDecimal(), y / t.toBigDecimal(), z / t.toBigDecimal())

    operator fun get(i: Int) = when (i) {
        0 -> x
        1 -> y
        2 -> z
        else -> error("index out of range")
    }

    fun toList() = listOf(x, y, z)
}