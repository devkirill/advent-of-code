package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution

class Day01 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines


    override fun part1(input: List<String>): Int {
        return input.map {
            it.replace(Regex("\\D"), "")
                .trim()
                .split("")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
        }
            .sumOf { it.first() * 10 + it.last() }
    }

    override fun part2(input: List<String>): Int {
        val map = mapOf(
            "one" to "1", "two" to "2", "three" to "3",
            "four" to "4", "five" to "5", "six" to "6",
            "seven" to "7", "eight" to "8", "nine" to "9"
        )
        return input.sumOf { line ->
            var nums = ""
            for (i in line.indices) {
                val l = line.substring(i)
                val ll = map.keys.firstOrNull { l.take(it.length) == it }
                if (ll != null) {
                    nums += map[ll]!!
                }
                if (line[i].isDigit()) {
                    nums += line[i]
                }
            }
            nums.split("").filter { it.isNotBlank() }.map { it.toInt() }
                .let { it.first() * 10 + it.last() }
        }
    }
}
