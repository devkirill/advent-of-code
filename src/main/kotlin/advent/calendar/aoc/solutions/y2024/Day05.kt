package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.splitEmptyLine
import advent.calendar.aoc.solutions.utils.toPair
import advent.calendar.aoc.solutions.y2024.Day05.Data

class Day05 : Solution<Data>() {
    override fun parse(lines: List<String>) = lines.splitEmptyLine()
        .let { (list, list2) ->
            Data(
                list.map { it.split("|").map { it.toInt() }.toPair() },
                list2.map { it.split(",").map { it.toInt() } }
            )
        }

    override fun part1(input: Data): Int {
        return input.rows
            .filter { row ->
                input.pairs.all { (a, b) ->
                    a !in row || b !in row || row.indexOf(a) < row.indexOf(b)
                }
            }
            .sumOf { it[it.size / 2] }
    }

    override fun part2(input: Data): Int {
        return input.rows
            .mapNotNull { row ->
                val ordered = row.sortedWith { o1, o2 ->
                    when {
                        o1 to o2 in input.pairs -> -1
                        o2 to o1 in input.pairs -> 1
                        else -> 0
                    }
                }
                if (ordered != row) ordered else null
            }
            .sumOf { it[it.size / 2] }
    }

    data class Data(
        val pairs: List<Pair<Int, Int>>,
        val rows: List<List<Int>>
    ) {
        val map = pairs.groupBy { it.second }.mapValues { it.value.map { it.first } }
    }
}
