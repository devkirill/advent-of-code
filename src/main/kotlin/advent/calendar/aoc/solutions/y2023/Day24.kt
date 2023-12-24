package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.geom.Vector3d
import advent.calendar.aoc.solutions.utils.pairs

class Day24 : Solution<List<Day24.Ray>>() {
    override fun parse(lines: List<String>) = lines
        .map { it.replace(" ", "") }
        .map { l ->
            val (a, b) = l.split("@")
                .map { ll ->
                    val (a, b, c) = ll.split(",").map { it.toInt() }
                    Vector3d(a, b, c)
                }
            Ray(a, b)
        }

    override fun part1(input: List<Ray>) = input.asSequence().pairs()
        .count { (a, b) ->

            true
        }

    data class Ray(val start: Vector3d, val step: Vector3d)
}
