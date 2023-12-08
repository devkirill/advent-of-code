package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.div
import advent.calendar.aoc.solutions.utils.lcm
import advent.calendar.aoc.solutions.utils.rem
import java.math.BigInteger

class Day11 : Solution<List<Day11.Monkey>>() {
    override fun parse(lines: List<String>) = lines.joinToString("\n") { it.trim() }
        .split("\n\n")
        .map { Monkey(it.split("\n")) }

    override fun part1(input: List<Monkey>) = solve(input, 20, false)

    fun solve(input: List<Monkey>, steps: Int, part2: Boolean): Long {
        var monkeys = input.toMutableList()
        val count = input.map { 0L }.toMutableList()
        val superMod = input.map { it.divisible }.reduce { a, b -> lcm(a, b) }.toBigInteger()
        repeat(steps) {
            for (i in monkeys.indices) {
                val monkey = monkeys[i]
                val moved =
                    monkey.items
                        .map { monkey.recalc(it) }
                        .map { it % superMod }
                        .let {
                            if (!part2) {
                                it.map { a -> a / 3 }
                            } else it
                        }
                        .map { it to monkey.next(it) }
                count[i] = count[i] + monkey.items.size.toLong()//.count { it != BigInteger.ZERO }.toLong()
                monkeys[i] = monkeys[i].copy(items = listOf())
                monkeys = monkeys.mapIndexed { i, monkey ->
                    monkey.copy(items = monkey.items + moved.filter { (_, b) -> b == i }.map { it.first })
                }.toMutableList()
            }
        }
        return count.sortedDescending().take(2).reduce { a, b -> a * b }
    }

    data class Monkey(
        val items: List<BigInteger>,
        val recalc: (BigInteger) -> BigInteger,
        val next: (BigInteger) -> Int,
        val divisible: Int
    ) {
        companion object {
            operator fun invoke(list: List<String>): Monkey {
                val items = list.first { "Starting items:" in it }
                    .substringAfter(":")
                    .replace(" ", "")
                    .split(",")
                    .map { it.toBigInteger() }
                val calc: (BigInteger) -> BigInteger = list.first { "Operation:" in it }
                    .substringAfter("=")
                    .trim()
                    .split(" ")
                    .let { (a, b, c) ->
                        { old ->
                            val A = if (a == "old") old else a.toBigInteger()
                            val C = if (c == "old") old else c.toBigInteger()
                            when (b) {
                                "+" -> A + C
                                "*" -> A * C
                                else -> TODO()
                            }
                        }
                    }
                val divisible = list.first { "divisible" in it }.split(" ").last().toInt()
                val next: (BigInteger) -> Int = { new ->
                    if (new % divisible == BigInteger.ZERO) {
                        list.first { "If true" in it }.split(" ").last().toInt()
                    } else {
                        list.first { "If false" in it }.split(" ").last().toInt()
                    }
                }
                return Monkey(items, calc, next, divisible)
            }
        }

        override fun toString() = "$items"
    }
}
