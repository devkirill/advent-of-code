package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.parse
import advent.calendar.aoc.solutions.utils.splitEmptyLine
import kotlin.math.max
import kotlin.math.min

class Day19 : Solution<Day19.Data>() {
    override fun parse(lines: List<String>) = lines
        .splitEmptyLine()
        .let { (a, b) ->
            val rules = a.associate {
                val (_, key, values) = it.parse("(.+)\\{(.+)\\}")
                key to values.split(",")
                    .map { k ->
                        if (":" in k) {
                            val (_, key, sign, value, step) = k.parse("(.*)([<>])(\\d+):(\\w+)")
                            Rule("xmas".indexOf(key), sign, value.toInt(), step)
                        } else {
                            Rule(0, ">", 0, k)
                        }
                    }
            }
            val parts = b.map { it.replace(Regex("[{}xmas=]"), "") }
                .map { l -> l.split(",").map { it.toInt() } }
            Data(rules, parts)
        }

    override fun part1(input: Data) = input.parts.filter { part ->
        fun resolve(part: List<Int>, step: String): String {
            val rules = input.rules[step] ?: return step
            return resolve(part, rules.first {
                if (it.sign == ">")
                    part[it.key] > it.value
                else
                    part[it.key] < it.value
            }.step)
        }
        resolve(part, "in") == "A"
    }.sumOf { it.sum() }

    override fun part2(input: Data): Long {
        fun resolve(part: List<IntRange>, step: String): Long {
            if (step == "A") return part.map { it.count().toLong() }.reduce { a, b -> a * b }
            val rules = input.rules[step] ?: return 0L
            val p = part.toMutableList()
            var result = 0L
            for ((key, sign, value, nextStep) in rules) {
                if (sign == ">" && p[key].last > value) {
                    val pp = p.toMutableList()
                    pp[key] = intersect(p[key], (value + 1)..4000)
                    p[key] = intersect(p[key], 1..value)
                    result += resolve(pp, nextStep)
                }
                if (sign == "<" && p[key].first < value) {
                    val pp = p.toMutableList()
                    pp[key] = intersect(p[key], 1 until value)
                    p[key] = intersect(p[key], value..4000)
                    result += resolve(pp, nextStep)
                }
            }
            return result
        }
        return resolve(listOf(1..4000, 1..4000, 1..4000, 1..4000), "in")
    }

    private fun intersect(a: IntRange, b: IntRange) = max(a.first, b.first)..min(a.last, b.last)

    data class Data(val rules: Map<String, List<Rule>>, val parts: List<List<Int>>)

    data class Rule(val key: Int, val sign: String, val value: Int, val step: String)
}
