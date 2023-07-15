import advent.calendar.aoc.solutions.utils.Matrix
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.assertEquals

fun main() {
    fun solves(input: Matrix) = Matrix(1..input.x.last, input.y.first..100)
        .indices
        .filter {
            var p = Point(0, 0)
            var shift = it
            while (p.x <= input.x.last && p.y >= input.y.first) {
                p += shift
                shift = Point(if (shift.x > 0) shift.x - 1 else 0, shift.y - 1)
                if (p in input) {
                    return@filter true
                }
            }
            false
        }

    fun part1(input: Matrix): Int {
        val shift = solves(input).maxByOrNull { it.y }!!
        return shift.y * (shift.y + 1) / 2
    }

    fun part2(input: Matrix): Int {
        return solves(input).size
    }

    val testInput = Matrix(20..30, -10..-5)
    assertEquals(part1(testInput), 45)
    assertEquals(part2(testInput), 112)

    val input = Matrix(241..275, -75..-49)
    println(part1(input))
    println(part2(input))
}
