package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.PointB
import advent.calendar.aoc.solutions.utils.geom.nearby4
import advent.calendar.aoc.solutions.utils.parse
import advent.calendar.aoc.solutions.utils.splitEmptyLine
import advent.calendar.aoc.solutions.utils.toInt
import advent.calendar.aoc.solutions.y2024.Day14.Robot
import java.math.BigInteger
import java.util.LinkedList

class Day14 : Solution<List<Robot>>() {
    override fun parse(lines: List<String>) =
        lines.map { line ->
            line.parse("""^p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)$""".toRegex()) {
                val (a, b, c, d) = it.toInt()
                Robot(Point(a, b), Point(c, d))
            }
        }

    val sx = 101
    val sy = 103

    fun Point.quadrant(): Int = when {
        x < sx / 2 && y < sy / 2 -> 1
        (sx - x - 1) < sx / 2 && y < sy / 2 -> 2
        (sx - x - 1) < sx / 2 && (sy - y - 1) < sy / 2 -> 3
        x < sx / 2 && (sy - y - 1) < sy / 2 -> 4
        else -> 0
    }

    fun List<Robot>.getAfter(step: Int) = map {
        val p = it.p + it.v * step
        val p2 = Point((p.x % sx + sx) % sx, (p.y % sy + sy) % sy)
        p2
    }

    override fun part1(input: List<Robot>) = input.getAfter(100)
        .groupBy { it.quadrant() }
        .filter { it.key != 0 }
        .map { it.value.size }
        .reduce { a, b -> a * b }

    override fun part2(input: List<Robot>): Any {
        var result = 0
        for (i in 0..10000) {
            val points = input.getAfter(i).toSet()
            val components = mutableListOf<Set<Point>>()
            val visited = mutableSetOf<Point>()
            for (p in points) {
                if (p !in visited) {
                    val r = mutableSetOf<Point>()
                    val queue = LinkedList<Point>().also { it.add(p) }
                    while (queue.isNotEmpty()) {
                        val c = queue.pop()
                        if (c !in r) {
                            r += c
                            for (o in c.nearby4()) {
                                if (o in points) {
                                    queue.add(o)
                                }
                            }
                        }
                    }
                    visited += r
                    components += r
                }
            }
            if (components.any { it.size > 40 }) {
                result = i
            }
        }

        return result
    }

    data class Robot(
        val p: Point,
        val v: Point
    )
}