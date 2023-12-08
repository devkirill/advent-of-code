package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Parser
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.splitPair
import advent.calendar.aoc.solutions.utils.toIntPoints
import kotlin.math.abs

class Day13 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    fun List<Point>.apply(dir: String, pos: Int): List<Point> {
        return if (dir == "x") {
            this.map { it.by { x = pos - abs(pos - x) } }.distinct()
        } else {
            this.map { it.by { y = pos - abs(pos - y) } }.distinct()
        }
    }

    override fun part1(input: List<String>): Int {
        var points = input.filter { "," in it }.toIntPoints(",")
        val folds = input.filter { "fold" in it }.map { it.replace("fold along ", "").splitPair("=") }

        for (fold in folds.take(1)) {
            points = points.apply(fold.first, fold.second.toInt())
        }
        return points.size
    }

    override fun part2(input: List<String>): String {
        var points = input.filter { "," in it }.toIntPoints(",")
        val folds = input.filter { "fold" in it }.map { it.replace("fold along ", "").splitPair("=") }

        for (fold in folds) {
            points = points.apply(fold.first, fold.second.toInt())
        }
        val list = mutableListOf<String>()
        for (y in points.minOf { it.y }..points.maxOf { it.y }) {
            list += (points.minOf { it.x }..points.maxOf { it.x })
                .joinToString("") { if (Point(it, y) in points) "█" else " " }
        }
        return Parser.parse(list)
    }
}
