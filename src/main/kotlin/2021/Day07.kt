import advent.calendar.aoc.solutions.utils.assertEquals
import advent.calendar.aoc.solutions.utils.readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val l = input[0].split(",").map(String::toInt)
        return (0..1000).map { p -> l.sumOf { abs(it - p) } }.minOf { it }
    }

    fun part2(input: List<String>): Int {
        val l = input[0].split(",").map(String::toInt)
        return (0..1000).map { p -> l.sumOf { abs(it - p) * (abs(it - p) + 1) / 2 } }.minOf { it }
    }

    val testInput = readInput("Day07_test")
    assertEquals(part1(testInput), 37)
    assertEquals(part2(testInput), 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
