package advent.calendar.aoc

import java.time.Year

abstract class Solution<T, R: Number>(val year: Int, val day: Int) {
    abstract fun parseInput(lines: List<String>): T

    abstract fun part1(t: T): R

    open fun part2(t: T): R {
        TODO("not ")
    }
}
