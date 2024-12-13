package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.PointB
import advent.calendar.aoc.solutions.utils.splitEmptyLine
import advent.calendar.aoc.solutions.y2024.Day13.Game
import java.math.BigInteger

class Day13 : Solution<List<Game>>() {
    override fun parse(lines: List<String>) = lines.splitEmptyLine().map {
        val (a, b) = it.take(2).map {
            it.split(": ")[1].split(", ").map { it.replace("X", "").replace("Y", "").toBigInteger() }
                .let { (x, y) -> PointB(x, y) }
        }
        val t = it[2].split(": ")[1].split(", ")
            .map { it.replace("X", "").replace("Y", "").replace("=", "").toBigInteger() }
            .let { (x, y) -> PointB(x, y) }
        Game(a, b, t)
    }

    override fun part1(input: List<Game>) = input.map { it.solve() }.reduce { a, b -> a + b }

    val s = BigInteger.valueOf(10000000000000L).let { PointB(it, it) }

    override fun part2(input: List<Game>) = part1(input.map { it.copy(target = it.target + s) })

    data class Game(
        val a: PointB,
        val b: PointB,
        val target: PointB
    ) {
        fun solve(): BigInteger {
            val d = a.x * b.y - a.y * b.x
            return if (d != BigInteger.ZERO) {
                val i = (b.y * target.x - b.x * target.y) / d
                val j = (a.x * target.y - a.y * target.x) / d
                if (a * i + b * j == target) {
                    i * 3.toBigInteger() + j
                } else {
                    BigInteger.ZERO
                }
            } else {
                BigInteger.ZERO
            }
        }
    }
}