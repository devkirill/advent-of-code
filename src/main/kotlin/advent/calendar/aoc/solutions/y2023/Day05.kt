package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day05 : Solution<Day05.Data>() {
    override fun parse(lines: List<String>): Data {
        val data = lines.joinToString("\n").split("\n\n").map { g -> g.split("\n") }
        val seeds = data.first().first().substringAfter(": ").split(" ").map { it.toLong() }
        val ranges = data.drop(1)
            .map { lines -> lines.drop(1).map { val (a, b, c) = it.split(" ").map { it.toLong() };Range(a, b, c) } }
        val rr = ranges.map { Mapper(it) }
        return Data(seeds, rr)
    }

    override fun part1(input: Data): Long {
        return input.seeds.minBy { r ->
            var it = r
            for (mapper in input.mappers) {
                it = mapper[it]
            }
            it
        }
    }

    data class Data(val seeds: List<Long>, val mappers: List<Mapper>)

    data class Mapper(val ranges: List<Range>) {
        operator fun get(i: Long) =
            ranges.firstOrNull { i >= it.get && i < it.get + it.size }?.let { it.insert - it.get + i } ?: i
    }

    data class Range(val get: Long, val insert: Long, val size: Long)
}
