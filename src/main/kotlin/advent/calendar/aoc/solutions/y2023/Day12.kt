package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution

class Day12 : Solution<List<Day12.Line>>() {
    override fun parse(lines: List<String>) = lines.map { line ->
        val (a, b) = line.split(" ")
        Line(a, b.split(",").map { it.toInt() })
    }

    override fun part1(input: List<Line>) = input.sumOf { it.variants() }

    override fun part2(input: List<Line>) = input
        .map { l -> Line((1..5).joinToString("?") { l.line }, (1..5).flatMap { l.nums }) }
        .sumOf { it.variants() }

    data class Line(val line: String, val nums: List<Int>) {
        fun variants(): Long {
            val groupSize = nums.size
            val cellSize = line.length

            val black = line.map { it == '#' }.toMutableList()
            val white = line.map { it == '.' }.toMutableList()

            fun canInsertBlack(cell: Int, length: Int): Boolean =
                cell + length <= cellSize && (cell until cell + length).none { white[it] }

            fun canInsertWhite(cell: Int): Boolean =
                cell >= cellSize || !black[cell]

            val calc = mutableMapOf<Int, MutableMap<Int, Long>>()

            fun calc(group: Int, cell: Int): Long {
                if (cell >= cellSize)
                    return if (group == groupSize) 1 else 0

                val res = calc[group]?.get(cell)
                if (res != null)
                    return res

                var variants = 0L
                val canInsert = group < groupSize
                        && canInsertBlack(cell, nums[group])
                        && canInsertWhite(cell + nums[group])
                if (canInsert) {
                    variants += calc(group + 1, cell + nums[group] + 1)
                }
                if (canInsertWhite(cell)) {
                    variants += calc(group, cell + 1)
                }

                calc.computeIfAbsent(group) { mutableMapOf() }[cell] = variants
                return variants
            }

            return calc(0, 0)
        }
    }
}
