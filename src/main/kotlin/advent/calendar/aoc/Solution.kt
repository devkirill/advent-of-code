package advent.calendar.aoc

import advent.calendar.aoc.exceptions.NotReleased

abstract class Solution<T>(val year: Int, val day: Int) {
//    constructor(year: Int) : this(year, this.javaClass.simpleName.let { it.substring(it.length - 2) }.toInt())

    abstract fun parse(lines: List<String>): T

    open fun parse2(lines: List<String>) = parse(lines)

    abstract fun part1(input: T): Any

    open fun part2(input: T): Any {
        throw NotReleased()
    }
}
