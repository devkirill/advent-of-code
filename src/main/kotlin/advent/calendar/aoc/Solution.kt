package advent.calendar.aoc

import advent.calendar.aoc.exceptions.NotReleased

abstract class Solution<T, R: Number>(val year: Int, val day: Int) {
    abstract fun parse(lines: List<String>): T

    open fun parse2(lines: List<String>) = parse(lines)

    abstract fun part1(input: T): R

    open fun part2(input: T): R {
        throw NotReleased()
    }
}
