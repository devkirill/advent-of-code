package advent.calendar.aoc.solutions.utils.geom

data class Vector3d(val x: Double, val y: Double, val z: Double) {
    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())

    operator fun plus(v: Vector3d) = Vector3d(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3d) = Vector3d(x - v.x, y - v.y, z - v.z)
    operator fun times(t: Double) = Vector3d(x * t, y * t, z * t)
    operator fun div(t: Double) = Vector3d(x / t, y / t, z / t)
}