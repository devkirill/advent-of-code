package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.dijkstraSearch
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.manhattanDistance
import advent.calendar.aoc.solutions.utils.geom.nearby4
import java.util.*
import kotlin.math.abs

class Day20 : Solution<Grid<String>>() {
    override fun parse(lines: List<String>): Grid<String> {
        if (!lines.all { it.all { it in "SE.#" } }) error("not valid")
        return Grid(lines) { "#" }
    }

    fun solve(input: Grid<String>, dist: Int): Int {
        val start = input.find { it == "S" }.single()
        val path = dijkstraSearch(listOf(start), { it.nearby4().filter { input[it] != "#" } }, { 1 })
        val jumps =
            (-dist..dist).flatMap { x -> (-dist..dist).map { y -> Point(x, y) } }.filter { abs(it.x) + abs(it.y) <= 20 }
        var result = 0
        val shorts = TreeMap<Int, Int>()
        for (p in path.keys) {
            for (n in jumps.map { it + p }.filter { it in path }) {
                val dist = ((path[n] ?: 0) - (path[p] ?: 0) - n.manhattanDistance(p))
                if (dist > 0) {
                    shorts[dist] = (shorts[dist] ?: 0) + 1
                }
                if (dist >= 100) {
                    result++
                }
            }
        }
        return result
    }

    override fun part1(input: Grid<String>) = solve(input, 2)

    override fun part2(input: Grid<String>) = solve(input, 20)
}
