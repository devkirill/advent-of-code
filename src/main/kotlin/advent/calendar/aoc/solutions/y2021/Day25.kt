package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.toMutableMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

class Day25 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        var map =
            input.indices.flatMap { y -> input[y].mapIndexed { x, c -> Point(x, y) to c } }.filter { it.second != '.' }
                .toMutableMap()
        var steps = 0
        do {
            var changed = false
            for ((c, shift) in listOf(
                '>' to Point(1, 0),
                'v' to Point(0, 1),
                '<' to Point(-1, 0),
                '^' to Point(0, -1)
            )) {
                val newMap = mutableMapOf<Point, Char>()
                map.entries.filter { it.value == c }
                    .map { (p, _) ->
                        p to (p + shift).by {
                            x = (x + input[0].length) % input[0].length
                            y = (y + input.size) % input.size
                        }
                    }
                    .forEach { (from, to) ->
                        if (to !in map && to !in newMap) {
                            newMap[to] = c
                        } else {
                            newMap[from] = c
                        }
                    }
                map.entries.filter { it.value != c }
                    .forEach { (p, c) -> newMap[p] = c }
                changed = changed || map != newMap
                map = newMap
            }

//            debug(steps)
//            for (y in input.indices) {
//                debug((input[0].indices).joinToString("") { (map[Point(it, y)] ?: '.').toString() })
//            }

            steps++
        } while (changed)
        return steps
    }
}
