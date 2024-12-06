package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Grid
import advent.calendar.aoc.solutions.utils.Position
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.y2024.Day06.Data

class Day06 : Solution<Data>() {
    override fun parse(lines: List<String>) = Data(Grid(lines) { "." })

    fun findResult(input: Data): Pair<Int, Boolean> {
        var officer = input.officer
        val visitedP = mutableSetOf<Point>()
        val visited = mutableSetOf<Position>()
        while (officer.x in 0 until input.size.x && officer.y in 0 until input.size.y) {
            if (officer in visited) {
                return visitedP.size to true
            }
            visited += officer
            visitedP += officer.p
            officer = if (officer.nextPos in input.walls) {
                officer.rotateCW()
            } else {
                officer.next
            }
        }
        return visitedP.size to false
    }

    override fun part1(input: Data): Any {
        return findResult(input).first
    }

    override fun part2(input: Data): Any {
        return input.empty.count { findResult(input.copy(walls = input.walls + it)).second }
    }

    data class Data(
        val walls: Set<Point>,
        val officer: Position,
        val size: Point,
        val empty: List<Point>
    ) {
        companion object {
            val dirs = listOf(">", "v", "<", "^")
            operator fun invoke(grid: Grid): Data {
                return Data(
                    walls = grid.find("#"),
                    officer = grid.find { it in dirs }.let { it.single().let { Position(it, dirs.indexOf(grid[it])) } },
                    size = grid.size,
                    empty = grid.data.keys.toList()
                ).let { data ->
                    data.copy(empty = data.empty.filter { it != data.officer.p && it !in data.walls })
                }
            }
        }
    }
}