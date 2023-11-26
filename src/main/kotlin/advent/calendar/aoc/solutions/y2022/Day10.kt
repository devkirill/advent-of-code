package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.*
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class Day10 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        var value = 1
        var tick = 1
        val data = mutableListOf(0)
        for (line in input) {
            val (c, v) = "$line 0".split(" ")
            if (c == "noop") {
                data += value
                tick++
            } else if (c == "addx") {
                data += value
                tick++
                data += value
                tick++
                value += v.toInt()
            }
        }
        return data.mapIndexed { tick, value -> if (tick % 40 == 20) value * tick else 0 }.sum()
    }

    override fun part2(input: List<String>): String {
        var value = 1
        var tick = 1
        val data = mutableListOf<Int>()
        for (line in input) {
            val (c, v) = "$line 0".split(" ")
            if (c == "noop") {
                data += value
                tick++
            } else if (c == "addx") {
                data += value
                tick++
                data += value
                tick++
                value += v.toInt()
            }
        }
        println()
        repeat(6) { y ->
            var s = ""
            for (x in 0 until 40) {
                val t = x + y * 40
                s += if (data.mapIndexed { i, t -> i to t }
                        .any { (tick, value) -> abs(value - x) < 2 && tick == t }) "█" else " "
            }
            println(s)
        }
        return readln()
    }
}
