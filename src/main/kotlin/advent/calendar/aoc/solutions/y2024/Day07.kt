package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.y2024.Day07.Data

class Day07 : Solution<List<Data>>() {
    override fun parse(lines: List<String>) = lines.map {
        val (a, b) = it.split(":").map { it.trim() }
        Data(a.toLong(), b.split(" ").map { it.trim().toLong() })
    }

    fun tryGetSum(sum: Long, ints: List<Long>, withConcat: Boolean = false): Boolean {
        if (sum == 0L && ints.isEmpty()) {
            return true
        }
        if (sum <= 0 || ints.isEmpty()) {
            return false
        }
        val l = ints.dropLast(1)
        val r = tryGetSum(sum - ints.last(), l, withConcat)
        if (r) return true

        if (ints.size > 1 && withConcat && sum.toString().endsWith(ints.last().toString())) {
            val l = ints.dropLast(1)
            val s2 = sum.toString().removeSuffix(ints.last().toString())
            if (s2.isNotEmpty()) {
                val sum = s2.toLong()
                val r = tryGetSum(sum, l, withConcat)
                if (r) return true
            }
        }

        val a = sum / ints.last()
        if (a * ints.last() == sum)
            return tryGetSum(a, l, withConcat)
        return false
    }

    override fun part1(input: List<Data>): Any {
        return input.filter { tryGetSum(it.sum, it.nums) }.sumOf { it.sum }
    }

    override fun part2(input: List<Data>): Any {
        return input.filter { tryGetSum(it.sum, it.nums, true) }.sumOf { it.sum }
    }

    data class Data(
        val sum: Long,
        val nums: List<Long>
    )
}