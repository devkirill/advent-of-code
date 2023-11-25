package advent.calendar.aoc

import advent.calendar.aoc.exceptions.NotReleased

abstract class Solution<T> {
    val year: Int get() = this.javaClass.packageName.removeWords().toInt()

    val day: Int get() = this.javaClass.simpleName.removeWords().toInt()

    fun String.removeWords() = filter { it in '0'..'9' }

    abstract fun parse(lines: List<String>): T

    open fun parse2(lines: List<String>) = parse(lines)

    abstract fun part1(input: T): Any

    open fun part2(input: T): Any {
        throw NotReleased()
    }
}
