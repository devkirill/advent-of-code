package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Matrix
import advent.calendar.aoc.solutions.utils.nearby8
import org.springframework.stereotype.Component

@Component
class Day11 : Solution<List<String>>(2021, 11) {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        val matrix = Matrix(input.indices, input[0].indices)
        val map = matrix.indices.associateWith { input[it].toString().toInt() }.toMutableMap()

        var result = 0
        repeat(100) {
            for (i in matrix.indices) {
                map[i] = map[i]!! + 1
            }

            do {
                var end = false
                for (p in matrix.indices) {
                    if (map[p]!! > 9) {
                        map[p] = 0
                        p.nearby8().filter { it in map && map[it] != 0 }.forEach { map[it] = map[it]!! + 1 }
                        end = true
                        result++
                    }
                }
            } while (end)
        }
        return result
    }

    override fun part2(input: List<String>): Int {
        val matrix = Matrix(input.indices, input[0].indices)
        val map = matrix.indices.associateWith { input[it].toString().toInt() }.toMutableMap()

        var result = 0
        while (map.values.any { it != 0 }) {
            for (i in matrix.indices) {
                map[i] = map[i]!! + 1
            }

            do {
                var end = false
                for (p in matrix.indices) {
                    if (map[p]!! > 9) {
                        map[p] = 0
                        p.nearby8().filter { it in map && map[it] != 0 }.forEach { map[it] = map[it]!! + 1 }
                        end = true
                    }
                }
            } while (end)
            result++
        }
        return result
    }
}
