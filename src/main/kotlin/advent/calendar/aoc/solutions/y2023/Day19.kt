package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.parse
import advent.calendar.aoc.solutions.utils.splitEmptyLine

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

    data class Data(val rules: Map<String, List<Rule>>, val parts: List<List<Int>>)

    data class Rule(val key: Int, val sign: String, val value: Int, val step: String)
}
