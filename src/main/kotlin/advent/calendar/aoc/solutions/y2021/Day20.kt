package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.around
import advent.calendar.aoc.solutions.utils.extend
import org.springframework.stereotype.Component

@Component
class Day20 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    fun run(input: List<String>, times: Int): Int {
        val a = input.first().mapIndexed { i, c -> i to c }.filter { it.second == '#' }.map { it.first }
        val list = input.drop(2)

        var rangeX = list.indices.extend(2 * times + 2)
        var rangeY = list[0].indices.extend(2 * times + 2)

        var points = mutableSetOf<Point>()
        for (y in list.indices) {
            val s = list[y]
            for (x in s.indices) {
                if (s[x] == '#') {
                    points += Point(x, y)
                }
            }
        }

        repeat(times) {
            val l = mutableSetOf<Point>()
            rangeX = rangeX.extend(-1)
            rangeY = rangeY.extend(-1)

            for (x in rangeX) {
                for (y in rangeY) {
                    val p = Point(x, y)
                    val n = p.around().map { if (it in points) 1 else 0 }.fold(0) { a, b -> a * 2 + b }
                    if (n in a) {
                        l += p
                    }
                }
            }

            points = l
        }

        return points.size
    }

    override fun part1(input: List<String>) = run(input, 2)

    override fun part2(input: List<String>) = run(input, 50)
}
