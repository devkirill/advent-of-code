package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.toInt
import org.springframework.stereotype.Component
import java.lang.Math.min
import java.util.Deque
import java.util.LinkedList

@Component
class Day25 : Solution<List<String>>(2022, 25) {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): String {
        return convert(input.sumOf { parse(it) })
    }

    fun parse(str: String): Long {
        var num = 0L
        for (c in str) {
            num *= 5
            when (c) {
                '=' -> num -= 2
                '-' -> num -= 1
                '1' -> num += 1
                '2' -> num += 2
            }
        }
        return num
    }

    fun convert(n: Long): String {
        var num = n
        var res = ""
        while (num != 0L) {
            when (num % 5) {
                0L, 1L, 2L -> {
                    res += "${num % 5}"
                    num /= 5
                }

                3L -> {
                    res += "="
                    num += 2
                    num /= 5
                }

                4L -> {
                    res += "-"
                    num += 1
                    num /= 5
                }
            }
        }
        return res.reversed()
    }
}
