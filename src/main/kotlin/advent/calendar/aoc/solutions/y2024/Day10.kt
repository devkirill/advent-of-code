package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.nearby4

class Day10 : Solution<Grid<Int>>() {
    override fun parse(lines: List<String>) = Grid(lines) { "0" }.map { it.toInt() }

    override fun part1(input: Grid<Int>): Any {
        fun find(p: Set<Point>, value: Int = 1): Set<Point> {
            if (value == 10) {
                return p
            }
            return find(p.flatMap { it.nearby4() }.filter { input[it] == value }.toSet(), value + 1)
        }
        return input.keys.filter { input[it] == 0 }.sumOf { p -> find(setOf(p)).size }
    }

    override fun part2(input: Grid<Int>): Any {
        fun find(p: Map<Point, Long>, value: Int = 1): Long {
            if (value == 10) {
                return p.values.sum()
            }
            return find(p.keys.flatMap { a -> a.nearby4().filter { input[it] == value }.map { it to (p[a] ?: 0) } }
                .groupBy { it.first }
                .mapValues { it.value.sumOf { it.second } }, value + 1)
        }
        return input.keys.filter { input[it] == 0 }.sumOf { p -> find(mapOf(p to 1)) }
    }
}