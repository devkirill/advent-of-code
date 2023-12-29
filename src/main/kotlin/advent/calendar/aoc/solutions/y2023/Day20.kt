package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.parse
import java.util.*

class Day20 : Solution<Map<String, Day20.Data>>() {
    val low = false
    val high = true

    override fun parse(lines: List<String>) = lines.associate {
        val (_, type, name, next) = it.parse("([%&]?)(\\w+) -> (.*)")
        name to Data(type, next.split(", "))
    }

    override fun part1(input: Map<String, Data>): Any {
        val queue: Queue<Pair<String, Boolean>> = LinkedList()
        repeat(1000) {
            queue += "button" to low
            while (queue.isNotEmpty()) {
                val (name, power) = queue.remove()
                if (name in input) {

                }
            }
        }
        return 0
    }

    data class Data(val type: String, val next: List<String>)
}
