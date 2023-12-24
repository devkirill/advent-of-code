package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Vector3bd
import advent.calendar.aoc.solutions.utils.pairs
import java.math.BigDecimal

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


    data class Ray(val p: Vector3bd, val v: Vector3bd) {
        val p2 = p + v
    }
}
