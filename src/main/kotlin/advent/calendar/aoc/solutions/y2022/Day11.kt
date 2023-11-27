package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.*
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class Day11 : Solution<List<Day11.Monkey>>() {
    override fun parse(lines: List<String>) = lines.joinToString("\n") { it.trim() }
        .split("\n\n")
        .map { Monkey(it.split("\n")) }

    override fun part1(input: List<Monkey>): Long {
        var monkeys = input.toMutableList()
        val count = input.map { 0L }.toMutableList()
        repeat(20) {
            for (i in monkeys.indices) {
                val monkey = monkeys[i]
                val moved = monkey.items.map { monkey.recalc(it) }.map { it to monkey.next(it) }
                count[i] = count[i] + monkey.items.size.toLong()
                monkeys[i] = monkeys[i].copy(items = listOf())
                monkeys =  monkeys.mapIndexed { i, monkey ->
                    monkey.copy(items = monkey.items + moved.filter { (_, b) -> b == i }.map { it.first } )
                }.toMutableList()
            }
        }
        return count.sortedDescending().take(2).reduce { a, b -> a * b }
    }

    data class Monkey(val items: List<Long>, val recalc: (Long) -> Long, val next: (Long) -> Int) {
        companion object {
            operator fun invoke(list: List<String>): Monkey {
                val items = list.first { "Starting items:" in it }
                    .substringAfter(":")
                    .replace(" ", "")
                    .split(",")
                    .map { it.toLong() }
                val calc: (Long) ->Long = list.first { "Operation:" in it }
                    .substringAfter("=")
                    .trim()
                    .split(" ")
                    .let { (a, b, c) ->
                        { old ->
                            val A = if (a == "old") old else a.toLong()
                            val C = if (c == "old") old else c.toLong()
                             val n = when (b) {
                                "+" -> A + C
                                "*" -> A * C
                                else -> TODO()
                            }
                            n/3
                        }
                    }
                val next: (Long) -> Int = { new ->
                    if (new % list.first { "divisible" in it }.split(" ").last().toInt() == 0L) {
                        list.first { "If true" in it }.split(" ").last().toInt()
                    } else {
                        list.first { "If false" in it }.split(" ").last().toInt()
                    }
                }
                return Monkey(items, calc, next)
            }
        }
    }
}
