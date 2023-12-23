package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.SquareMap
import advent.calendar.aoc.solutions.utils.findComp
import advent.calendar.aoc.solutions.utils.nearby4
import advent.calendar.aoc.solutions.utils.toSquareMap

class Day21 : Solution<SquareMap>() {
    override fun parse(lines: List<String>) = lines.toSquareMap()

    override fun part1(input: SquareMap): Int {
        val ss = input.keys.filter { input[it] == '#' }.toSet()
        val comps = findComp(ss.toList()) { p -> p.nearby4().filter { it in ss } }
        var pos = input.keys.filter { input[it] == 'S' }
        var c = 0
        var result = 0
        repeat(64) { step ->
            pos = pos.flatMap { it.nearby4() }.filter { input[it] == '.' || input[it] == 'S' }.distinct()
            val nears = pos.flatMap { it.nearby4() }.toSet()
            val n = comps.filter { c -> c.any { p -> p in nears } }.size
            if (n > c) {
                c = n
                result = pos.size
            }
        }
        return result
    }
}
