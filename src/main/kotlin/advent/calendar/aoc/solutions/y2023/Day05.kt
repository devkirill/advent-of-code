package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import java.lang.Long.min
import kotlin.math.max

class Day05 : Solution<Day05.Data>() {
    override fun parse(lines: List<String>): Data {
        val data = lines.joinToString("\n").split("\n\n").map { g -> g.split("\n") }
        val seeds = data.first().first().substringAfter(": ").split(" ").map { it.toLong() }
        val ranges = data.drop(1)
            .map { lines ->
                lines.drop(1).map { val (a, b, c) = it.split(" ").map { it.toLong() };Range(b until (b + c), a - b) }
            }
        val rr = ranges.map { Mapper(it) }
        return Data(seeds, rr)
    }

    override fun part1(input: Data) = input.seeds
        .map { it..it }
        .flatMap { r ->
            input.mappers.fold(listOf(r)) { ranges, mapper -> ranges.flatMap { it.apply(mapper) } }
        }
        .minOf { it.first }

    override fun part2(input: Data) = input.seeds
        .chunked(2)
        .map { it[0] until (it[0] + it[1]) }
        .flatMap { r ->
            input.mappers.fold(listOf(r)) { ranges, mapper -> ranges.flatMap { it.apply(mapper) } }
        }
        .minOf { it.first }

    data class Data(val seeds: List<Long>, val mappers: List<Mapper>)

    data class Mapper(val ranges: List<Range>)

    data class Range(val range: LongRange, val offset: Long) {
        fun intersect(another: LongRange): Range? {
            val r = max(range.first, another.first)..min(range.last, another.last)
            return if (r.first <= r.last) {
                return Range(r, offset)
            } else null
        }
    }

    fun LongRange.apply(mapper: Mapper): List<LongRange> {
        val result = mutableListOf<LongRange>()
        mapper.ranges.forEach { range ->
            val intersect = range.intersect(this)
            if (intersect != null) {
                result += intersect.range.apply(intersect)
            }
        }
        result += this.sub(mapper.ranges.map { it.range })
        return result
    }

    fun LongRange.apply(range: Range) = (first + range.offset)..(last + range.offset)

    fun LongRange.sub(range: LongRange) =
        if (max(first, range.first) <= min(last, range.last)) {
            listOf(first until range.first, (range.last + 1) until last)
                .filter { it.first <= it.last }
        } else listOf(this)

    fun LongRange.sub(ranges: List<LongRange>): List<LongRange> {
        var result = listOf(this)
        ranges.forEach { range ->
            result = result.flatMap { it.sub(range) }
        }
        return result
    }

//    data class Range(val insert: Long, val get: Long, val size: Long)
}
