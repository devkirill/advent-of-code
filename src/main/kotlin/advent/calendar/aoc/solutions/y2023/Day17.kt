package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.SquareMap
import advent.calendar.aoc.solutions.utils.dijkstraSearch
import advent.calendar.aoc.solutions.utils.toSquareMap

class Day17 : Solution<SquareMap>() {
    override fun parse(lines: List<String>) = lines.toSquareMap()

    override fun part1(input: SquareMap) = solve(input, 1..3)
    override fun part2(input: SquareMap) = solve(input, 4..10)

    fun solve(input: SquareMap, steps: IntRange): Int {
        val result = dijkstraSearch(
            listOf(Position(Point(0, 0), 0, steps = 0), Position(Point(0, 0), 1, steps = 0)),
            { pos ->
                val list = mutableListOf<Position>()
                if (pos.steps >= steps.first) {
                    list += pos.copy(dir = pos.dir + 1, steps = 1)
                    list += pos.copy(dir = pos.dir + 3, steps = 1)
                }
                if (pos.steps < steps.last) {
                    list += pos.copy(steps = pos.steps + 1)
                }
                list.map { it.next }.filter { it.p in input }
            },
            { pos -> input[pos.p].toString().toInt() }
        )
        val m = result
            .filter { it.key.p == input.bottomRight }
            .filter { it.key.steps in steps }
            .minBy { it.value }.key
//        val path = result.getPathTo(m).map { it.p }
//        println()
//        println(input.copy(data = input.data.map { (k, v) -> k to (if (k in path) '.' else v) }.toMap()))
        return result[m]!!
    }

    data class Position(val p: Point, var dir: Int, val steps: Int = 1) {
        init {
            dir %= 4
        }

        val shift = when (dir) {
            0 -> Point(1, 0)
            1 -> Point(0, 1)
            2 -> Point(-1, 0)
            3 -> Point(0, -1)
            else -> TODO()
        }
        val nextPos = p + shift
        val next by lazy { copy(p = nextPos) }
        fun rotateCW() = Position(p, dir + 1)
        fun rotateCCW() = Position(p, dir + 3)
    }
}
