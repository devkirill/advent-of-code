package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.contains
import org.springframework.stereotype.Component

@Component
class Day08 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        var result = 0
        for (str in input) {
            val s = str.split(" | ").map { it.split(" ") }
            result += s[1].filter { it.length in setOf(2, 3, 4, 7) }.size
        }
        return result
    }

    override fun part2(input: List<String>): Int {
        var result = 0
        for (str in input) {
            val s = str.split(" | ").map { it.split(" ").map { x -> x.toSet() } }
            val map = mutableMapOf(
                1 to s[0].first { it.size == 2 },
                4 to s[0].first { it.size == 4 },
                7 to s[0].first { it.size == 3 },
                8 to s[0].first { it.size == 7 }
            )
            map[9] = s[0].first { it.size == 6 && map[4]!! in it }
            map[0] = s[0].first { it.size == 6 && map[1]!! in it && it !in map.values }
            map[6] = s[0].first { it.size == 6 && it !in map.values }
            map[3] = s[0].first { it.size == 5 && map[1]!! in it }
            map[5] = s[0].first { it.size == 5 && it in map[6]!! }
            map[2] = s[0].first { it.size == 5 && it !in map.values }
            val num = s[1].map { x -> map.keys.first { map[it] == x } }.joinToString("")
//            debug(num)
            result += num.toInt()
        }
        return result
    }
}
