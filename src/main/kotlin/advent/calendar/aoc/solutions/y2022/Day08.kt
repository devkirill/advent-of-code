package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Point

class Day08 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        val map =
            input.mapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to "$c".toInt() } }.flatten().toMap()
        val viewed = mutableSetOf<Point>()
        fun check(start: Point, dir: Point) {
            var h = -1
            var p = start
            while (p in map) {
                if (map[p]!! > h) {
                    viewed += p
                    h = map[p]!!
                }
                p += dir
            }
        }

        (input.indices).map { y ->
            check(Point(0, y), Point(1, 0))
            check(Point(input[0].length - 1, y), Point(-1, 0))
        }
        (input[0].indices).map { x ->
            check(Point(x, 0), Point(0, 1))
            check(Point(x, input.size - 1), Point(0, -1))
        }
        return viewed.size
    }

    override fun part2(input: List<String>): Int {
        val map =
            input.mapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to "$c".toInt() } }.flatten().toMap()

        fun check(start: Point, startH: Int, dir: Point): Int {
            var p = start + dir
            var r = 0
            while (p in map) {
                r++
                val h = map[p]!!
                if (h >= startH) {
                    break
                }
                p += dir
            }
            return r
        }

        return map.entries.maxOf { (k, v) ->
            listOf(Point(0, -1), Point(1, 0), Point(0, 1), Point(-1, 0))
                .map { check(k, v, it) }.reduce { a, b -> a * b }
        }
    }
}
