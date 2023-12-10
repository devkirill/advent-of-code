package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.intersect
import java.util.*

class Day10 : Solution<Day10.Graph>() {
    override fun parse(lines: List<String>): Graph {
        var start: Point? = null
        val g = mutableMapOf<Point, List<Point>>()
        val c = mutableMapOf<Point, Char>()
        for (y in lines.indices) {
            for (x in lines[0].indices) {
                val p = Point(x, y)
                g[p] = when (lines[y][x]) {
                    '|' -> listOf(Point(0, -1), Point(0, 1))
                    '-' -> listOf(Point(-1, 0), Point(1, 0))
                    'L' -> listOf(Point(0, -1), Point(1, 0))
                    'J' -> listOf(Point(0, -1), Point(-1, 0))
                    '7' -> listOf(Point(0, 1), Point(-1, 0))
                    'F' -> listOf(Point(0, 1), Point(1, 0))
                    '.' -> listOf()
                    'S' -> {
                        start = p
                        listOf()
                    }

                    else -> TODO("${lines[y][x]}?")
                }.map { it + p }
                c[p] = lines[y][x]
            }
        }
        g[start!!] = g.keys.filter { start in g[it]!! }
        return Graph(start, g, c)
    }

    override fun part1(input: Graph) = find(input).size / 2

    override fun part2(input: Graph): Int {
        val path = find(input)
        val pairs = path zip (path.drop(1) + path.first())
        return input.graph.keys.filter { it !in path }.filter { a ->
            val b = Point(a.x + 10000, a.y - 1)
            pairs.count { intersect(a, b, it.first, it.second) } % 2 == 1
        }.count()
    }

    fun find(input: Graph): List<Point> {
        val visited = mutableSetOf<Point>()
        val queue = LinkedList(listOf(input.start))
        while (queue.isNotEmpty()) {
            val cur = queue.pollFirst()
            if (cur !in visited) {
                visited += cur
                for (next in input.graph[cur] ?: listOf()) {
                    queue.push(next)
                }
            }
        }
        return visited.toList()
    }

    data class Graph(val start: Point, val graph: Map<Point, List<Point>>, val chars: Map<Point, Char>)
}

