package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.DijkstraResult
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.Position
import advent.calendar.aoc.solutions.utils.dijkstraSearch
import advent.calendar.aoc.solutions.utils.geom.Point

class Day16 : Solution<Grid<String>>() {
    override fun parse(lines: List<String>) = Grid(lines) { "#" }

    fun solve(start: Position, input: Grid<String>): DijkstraResult<Position> {
        val result = dijkstraSearch(listOf(start)) {
            listOf(
                it.next to 1,
                it.rotateCW() to 1000,
                it.rotateCCW() to 1000
            ).filter { input[it.first.p] != "#" }
        }
        return result
    }

    override fun part1(input: Grid<String>): Any {
        val start = Position(input.find { it == "S" }.single(), 0)
        val result = solve(start, input)
        val end = input.find { it == "E" }.single()
        return (0 until 4).map { Position(end, it) }.minOf { result[it] ?: 0 }
    }

    override fun part2(input: Grid<String>): Any {
        val start = Position(input.find { it == "S" }.single(), 0)
        val result = solve(start, input)
        val end = input.find { it == "E" }.single()
        val best = (0 until 4).map { Position(end, it) }.minOf { result[it] ?: 0 }
        fun calc(p: Position): Set<Point> {
            if (p == start) return setOf(p.p)
            val list = result.prevs[p] ?: mutableListOf()
            return (list.flatMap { calc(it) } + p.p).toSet()
        }
        return (0 until 4).map { Position(end, it) }.filter { result[it] == best }.flatMap { calc(it) }.toSet().size
    }
}