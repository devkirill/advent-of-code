package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day06 : Solution<Day06.Data>() {
    override fun parse(lines: List<String>) = lines
        .map { l -> l.split(" ").filter { it.isNotBlank() }.drop(1).map { it.toLong() } }
        .let { Data(it[0] zip it[1]) }

    override fun part1(input: Data) = input.times.map { (time, dist) ->
        (0..time).count { t -> t * (time - t) > dist }
    }.reduce { a, b -> a * b }

    override fun part2(input: Data) =
        input.times.reduce { (t1, d1), (t2, d2) -> "$t1$t2".toLong() to "$d1$d2".toLong() }
            .let { (time, dist) ->
                (0..time).count { t -> t * (time - t) > dist }
            }

    data class Data(val times: List<Pair<Long, Long>>)
}
