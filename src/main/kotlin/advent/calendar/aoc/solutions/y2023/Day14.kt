package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point

class Day14 : Solution<Day14.Map>() {
    override fun parse(lines: List<String>): Map {
        val sizeX = lines[0].length
        val sizeY = lines.size
        val sq = mutableSetOf<Point>()
        val rounds = mutableSetOf<Point>()
        for (y in lines.indices) {
            for (x in lines[0].indices) {
                val p = Point(x, y)
                if (lines[y][x] == '#') {
                    sq += p
                }
                if (lines[y][x] == 'O') {
                    rounds += p
                }
            }
        }
        return Map(sizeX, sizeY, sq, rounds)
    }

    override fun part1(input: Map) = input.north().rounds.sumOf { input.sizeY - it.y }
    override fun part2(input: Map): Int {
        var cur = input
        val order = mutableListOf<Map>()
        while (cur !in order) {
            order += cur
            cur = cur.circle()
        }
        val s = order.indexOf(cur)
        val r = order[(1000000000 - s) % (order.size - s) + s]
        return r.weight
    }

    data class Map(val sizeX: Int, val sizeY: Int, val squares: Set<Point>, val rounds: Set<Point>) {
        val weight = rounds.sumOf { sizeY - it.y }

        fun north() = action(Point(0, -1)) { it.y }

        fun circle() = action(Point(0, -1)) { it.y }
            .action(Point(-1, 0)) { it.x }
            .action(Point(0, 1)) { -it.y }
            .action(Point(1, 0)) { -it.x }

        fun action(shift: Point, order: (Point) -> Int): Map {
            val newRounds = mutableSetOf<Point>()
            for (p in rounds.sortedBy { order(it) }) {
                val n = p + shift
                newRounds += if (n !in squares && n !in newRounds && n.x >= 0 && n.y >= 0 && n.x < sizeX && n.y < sizeY) n else p
            }
            val upd = this.copy(rounds = newRounds)
            return if (upd == this) upd else upd.action(shift, order)
        }

        override fun toString(): String {
            return (0 until sizeY).map { y ->
                (0 until sizeX).map { x ->
                    when (Point(x, y)) {
                        in squares -> '#'
                        in rounds -> 'O'
                        else -> '.'
                    }
                }.joinToString("")
            }.joinToString("\n")
        }
    }
}
