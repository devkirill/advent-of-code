package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.*
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.PointB
import advent.calendar.aoc.solutions.utils.geom.nearby4
import advent.calendar.aoc.solutions.y2024.Day15.Game
import java.awt.Color.red
import java.math.BigInteger
import java.util.LinkedList

class Day15 : Solution<Game>() {
    override fun parse(lines: List<String>) = lines.splitEmptyLine().let { (p1, p2) ->
        val grid = Grid(p1)
        Game(
            grid.map { if (it == "@") "." else it },
            grid.find("@").single(),
            actions = p2.joinToString("").map { ">v<^".indexOf(it) }
        )
    }

    override fun part1(input: Game) = input.actions.fold(input.grid to input.robot) { pair, dir ->
        pair.move(dir)
    }.let { (grid, _) ->
        grid.keys
            .filter { grid[it] == "O" || grid[it] == "[" }
            .sumOf { it.y * 100 + it.x }
    }

    override fun parse2(lines: List<String>) = lines.splitEmptyLine().let { (p1, p2) ->
        val grid = Grid(p1.map { line ->
            line.map {
                when (it) {
                    'O' -> "[]"
                    '#' -> "##"
                    '@' -> "@."
                    else -> ".."
                }
            }.joinToString("")
        })
        Game(
            grid.map { if (it == "@") "." else it },
            grid.find("@").single(),
            actions = p2.joinToString("").map { ">v<^".indexOf(it) }
        )
    }

    override fun part2(input: Game) = input.actions.fold(input.grid to input.robot) { pair, dir ->
        pair.move(dir)
    }.let { (grid, _) ->
        grid.keys
            .filter { grid[it] == "O" || grid[it] == "[" }
            .sumOf { it.y * 100 + it.x }
    }

    fun Pair<Grid<String>, Point>.move(dir: Int): Pair<Grid<String>, Point> {
        val grid = first
        val robot = second
        val group = mutableSetOf<Point>()
        val visited = mutableSetOf<Point>()
        val queue = LinkedList<Point>().also { it.add(robot) }
        while (queue.isNotEmpty()) {
            val c = queue.removeFirst().move(dir)
            if (c in visited) continue
            visited += c
            if (grid[c] == "#") {
                return this
            }
            if (grid[c] != ".") {
                group += c
                queue.add(c)
            }
            if (grid[c] == "[") {
                queue.add(c + Point(1, 0) - Point.ZERO.move(dir))
            }
            if (grid[c] == "]") {
                queue.add(c + Point(-1, 0) - Point.ZERO.move(dir))
            }
        }
        val d = group.map { it to "." } + group.map { it.move(dir) to grid[it] }
        val g = grid.copy(data = grid.data + d.toMap())
        return g to robot.move(dir)
    }

    data class Game(
        val grid: Grid<String>,
        val robot: Point,
        val actions: List<Int>
    )
}