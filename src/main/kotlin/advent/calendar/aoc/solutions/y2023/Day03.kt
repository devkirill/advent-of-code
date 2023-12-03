package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.nearby8
import org.springframework.stereotype.Component
import kotlin.math.max

@Component
class Day03 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        val points = mutableListOf<Point>()
        for (y in input.indices) {
            for (x in input[0].indices) {
                val c = input[y][x]
                if (!c.isDigit() && c != '.') {
                    points += Point(x, y)
                }
            }
        }
        return findNear(points, input).sum()
    }

    override fun part2(input: List<String>): Int {
        val gears = mutableListOf<Int>()
        for (y in input.indices) {
            for (x in input[0].indices) {
                val c = input[y][x]
                if (c == '*') {
                    val near = findNear(listOf(Point(x,y)), input)
                    if (near.size == 2) {
                        gears += near.reduce {a,b -> a*b}
                    }
                }
            }
        }
        return gears.sum()
    }

    fun findNear(points: List<Point>, input: List<String>): List<Int> {
val nears = points.flatMap { it.nearby8() }.toSet()
        val nums = mutableListOf<Int>()
        for (y in input.indices) {
            var num = ""
            var isNear = false
            for (x in input[0].indices) {
                val c = input[y][x]
                if (c.isDigit()) {
                    num += c
                    if (Point(x,y) in nears) {
                        isNear = true
                    }
                } else {
                    if (num.isNotBlank() && isNear) {
                        nums += num.toInt()
                    }
                    num = ""
                    isNear = false
                }
            }
            if (num.isNotBlank() && isNear) {
                nums += num.toInt()
            }
        }
        return nums
    }
}
