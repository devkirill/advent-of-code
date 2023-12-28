package advent.calendar.aoc.solutions.y2023

import Jama.Matrix
import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Vector3bd
import advent.calendar.aoc.solutions.utils.geom.div
import advent.calendar.aoc.solutions.utils.pairs
import java.math.BigDecimal
import java.math.RoundingMode

class Day24 : Solution<List<Day24.Ray>>() {
    override fun parse(lines: List<String>) = lines
        .map { it.replace(" ", "") }
        .map { l ->
            val (a, b) = l.split("@")
                .map { ll ->
                    val (a, b, c) = ll.split(",").map { it.toDouble() }
                    Vector3bd(a, b, c)
                }
            Ray(a, b)
        }

    override fun part1(input: List<Ray>) = input.asSequence()
        .pairs()
        .count { (a, b) ->
            val (x1, y1) = a.p
            val (x2, y2) = a.p2
            val (x3, y3) = b.p
            val (x4, y4) = b.p2
            val d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)
            if (d.abs() < 1e-9.toBigDecimal()) {
                false
            } else {
                val px = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d
                val py = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d
                val p = Vector3bd(px, py, 0.0.toBigDecimal())
                val sign1 =
                    (p.x - a.p.x) * a.v.x.signum().toBigDecimal() >= BigDecimal.ZERO && (p.y - a.p.y) * a.v.y.signum()
                        .toBigDecimal() >= BigDecimal.ZERO
                val sign2 =
                    (p.x - b.p.x) * b.v.x.signum().toBigDecimal() >= BigDecimal.ZERO && (p.y - b.p.y) * b.v.y.signum()
                        .toBigDecimal() >= BigDecimal.ZERO
                sign1 && sign2 && px.toDouble() in 2.0E14..4.0E14 && py.toDouble() in 2.0E14..4.0E14
            }
        }

    override fun part2(input: List<Ray>): Any {

        fun getPosAndTime(a: Int, b: Int): List<Double> {
            val (p1, v1) = input[a] - input[0]
            val (p2, v2) = input[b] - input[0]

            val matrix = Matrix(
                arrayOf(
                    doubleArrayOf(p1.x.toDouble(), v1.x.toDouble(), -v2.x.toDouble()),
                    doubleArrayOf(p1.y.toDouble(), v1.y.toDouble(), -v2.y.toDouble()),
                    doubleArrayOf(p1.z.toDouble(), v1.z.toDouble(), -v2.z.toDouble())
                )
            )
            val vector = Matrix(
                arrayOf(
                    doubleArrayOf(p2.x.toDouble()),
                    doubleArrayOf(p2.y.toDouble()),
                    doubleArrayOf(p2.z.toDouble()),
                )
            )
            val res = vector / matrix
            val t2 = res[2, 0]
            val posAt2 = input[b].p + input[b].v * t2
            return listOf(t2) + posAt2.toList().map { it.toDouble() }
        }
        val (t1, x1, y1, z1) = getPosAndTime(1, 2)
        val (t2, x2, y2, z2) = getPosAndTime(1, 3)
        val vx = (x2 - x1) / (t2 - t1)
        val vy = (y2 - y1) / (t2 - t1)
        val vz = (z2 - z1) / (t2 - t1)
        val x = BigDecimal(x1 - t1 * vx).setScale(0, RoundingMode.HALF_UP)
        val y = BigDecimal(y1 - t1 * vy).setScale(0, RoundingMode.HALF_UP)
        val z = BigDecimal(z1 - t1 * vz).setScale(0, RoundingMode.HALF_UP)
        return x + y + z
    }

    data class Ray(val p: Vector3bd, val v: Vector3bd) {
        val p2 = p + v

        operator fun minus(r: Ray) = Ray(p - r.p, v - r.v)
    }
}
