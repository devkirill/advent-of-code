package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.nearby4
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.contains
import kotlin.collections.filter
import kotlin.collections.forEach
import kotlin.collections.indices
import kotlin.collections.isNotEmpty
import kotlin.collections.minOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set

@Component
class Day12 : Solution<Day12.WorldMap>() {
    override fun parse(lines: List<String>): WorldMap {
        var s = Point(0, 0)
        var e = Point(0, 0)
        var map = mutableMapOf<Point, Int>()
        for (y in lines.indices) {
            for (x in lines[0].indices) {
                val p = Point(x, y)
                val c = lines[y][x]
                val w = when (c) {
                    in 'a'..'z' -> c - 'a'
                    'E' -> {
                        e = p
                        'z' - 'a'
                    }

                    'S' -> {
                        s = p
                        0
                    }

                    else -> TODO()
                }
                map[p] = w
            }
        }
        return WorldMap(s, e, map)
    }

    override fun part1(input: WorldMap) = dist(input, input.s)

    override fun part2(input: WorldMap) = input.g.keys.filter { input.g[it] == 0 }.minOf { dist(input, it) }

    fun dist(input: WorldMap, s: Point): Int {
        val result = mutableMapOf(s to 0)
        val queue = LinkedList(result.keys)
        while (queue.isNotEmpty()) {
            val c = queue.pollFirst()
            c.nearby4()
                .filter { it in input.g }
                .filter { input.g[it]!! <= input.g[c]!! + 1 }
                .filter { it !in result }
                .forEach {
                    result[it] = result[c]!! + 1
                    queue.add(it)
                }
        }
        return result[input.e] ?: 10000
    }

    data class WorldMap(val s: Point, val e: Point, val g: Map<Point, Int>)
}
