package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution

class Day02 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Long {
        var x = 0L
        var y = 0L
        for (i in input) {
            val s = i.split(" ")
            val c = s[1].toInt()
            when (s[0]) {
                "forward" -> x += c
                "up" -> y -= c
                "down" -> y += c
            }
        }
        return x * y
    }

    override fun part2(input: List<String>): Long {
        var x = 0L
        var y = 0L
        var z = 0L
        for (i in input) {
            val s = i.split(" ")
            val c = s[1].toInt()
            when (s[0]) {
                "forward" -> {
                    x += c
                    y += c * z
                }

                "up" -> z -= c
                "down" -> z += c
            }
//            debug(x, y, z)
        }
        return x * y
    }
}
