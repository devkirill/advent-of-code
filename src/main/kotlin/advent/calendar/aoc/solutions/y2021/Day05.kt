package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.toIntPoints
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class Day05 : Solution<List<String>, Int>(2021, 5) {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        val lines = input.map { it.split(" -> ").toIntPoints(",") }
        val map = mutableMapOf<Point, Int>()
        for (l in lines) {
            val from = l[0]
            val v = l[1] - from
            if (v.x == 0 || v.y == 0) {
                val max = Integer.max(abs(v.x), abs(v.y))
                for (i in 0..max) {
                    val p = from + (v / max * i)
                    map[p] = (map[p] ?: 0) + 1
                }
            }
        }
        return map.values.filter { it > 1 }.size
    }

    override fun part2(input: List<String>): Int {
        val lines = input.map { it.split(" -> ").toIntPoints(",") }
        val map = mutableMapOf<Point, Int>()
        for (l in lines) {
            val from = l[0]
            val v = l[1] - from

            val max = Integer.max(abs(v.x), abs(v.y))
            for (i in 0..max) {
                val p = from + (v / max * i)
                map[p] = (map[p] ?: 0) + 1
            }
        }
        return map.values.filter { it > 1 }.size
    }
}
