package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.splitEmptyLine

class Day13 : Solution<List<Day13.Map>>() {
    override fun parse(lines: List<String>) = lines.splitEmptyLine().map { ll ->
        val sx = ll[0].length
        val sy = ll.size
        val points = mutableListOf<Point>()
        for (y in 0 until sy) {
            for (x in 0 until sx) {
                if (ll[y][x] == '#') points += Point(x, y)
            }
        }
        Map(sx, sy, points)
    }

    override fun part1(input: List<Map>) = input.sumOf { map ->
        map.calc().num
    }

    override fun part2(input: List<Map>) = input.sumOf { map ->
        val base = map.calc()
        var res = base
        for (x in 0 until map.sizeX) {
            for (y in 0 until map.sizeY) {
                val p = Point(x, y)
                val newMap = if (p in map.points) {
                    map.copy(points = map.points.filter { it != p })
                } else {
                    map.copy(points = map.points + p)
                }
                val calc = newMap.calc()
                if (calc.size > res.size || calc.size == res.size && (calc - base).size == 1) {
                    res = calc
                }
            }
        }
        val num = (res - base).num
        num
    }

    data class Map(val sizeX: Int, val sizeY: Int, val points: List<Point>) {
        val rows = (0 until sizeY).map { y -> points.filter { it.y == y }.map { it.x }.sorted() }
        val cols = (0 until sizeX).map { x -> points.filter { it.x == x }.map { it.y }.sorted() }

        fun calc(): ResultModel {
            val rows = rows.map { it.hashCode() }.toList()
            val cols = cols.map { it.hashCode() }.toList()
            fun checkSplit(p: Int, list: List<Int>, size: Int): Boolean {
                for (l in 1..p) {
                    val r = p + 1 + (p - l)
                    if (r <= size && list[l - 1] != list[r - 1]) {
                        return false
                    }
                }
                return true
            }

            val a = (1 until sizeX).filter { checkSplit(it, cols, sizeX) }
            val b = (1 until sizeY).filter { checkSplit(it, rows, sizeY) }
            return ResultModel(a, b)
        }
    }

    data class ResultModel(val cols: List<Int> = listOf(), val rows: List<Int> = listOf()) {
        val num = rows.sum() * 100 + cols.sum()
        val size = cols.size + rows.size

        operator fun minus(other: ResultModel) =
            ResultModel(cols.filter { it !in other.cols }, rows.filter { it !in other.rows })
    }
}
