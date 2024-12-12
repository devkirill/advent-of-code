package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.Position
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.nearby4
import java.util.*

class Day12 : Solution<Grid<String>>() {
    override fun parse(lines: List<String>) = Grid(lines)

    fun findRegions(grid: Grid<String>): List<Set<Point>> {
        val result = mutableListOf<Set<Point>>()
        val visited = mutableSetOf<Point>()
        for (p in grid.keys) {
            if (p !in visited) {
                val group = mutableSetOf<Point>()
                val next = LinkedList<Point>().apply { add(p) }
                while (next.isNotEmpty()) {
                    val cur = next.removeFirst()
                    if (grid[p] == grid[cur] && cur !in group) {
                        group += cur
                        for (c in cur.nearby4()) {
                            next.add(c)
                        }
                    }
                }
                visited += group
                result += group
            }
        }
        return result
    }

    override fun part1(input: Grid<String>): Any {
        return findRegions(input).sumOf { group ->
            val s = group.size
            val p = group.flatMap { it.nearby4() }.count { it !in group }
            s * p
        }
    }

    override fun part2(input: Grid<String>): Any {
        return findRegions(input).sumOf { group ->
            val s = group.size
            fun calc(dir: Position): Int {
                val d = dir.nextPos
                val n = dir.rotateCW().nextPos
                return group.count { (it + d) !in group && ((it + n) !in group || (it + d + n) in group) }
            }

            val p = (0 until 4).map { Position(Point(0, 0), dir = it) }.sumOf { calc(it) }
            s * p
        }
    }
}