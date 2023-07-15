import advent.calendar.aoc.solutions.utils.assertEquals
import advent.calendar.aoc.solutions.utils.middle
import advent.calendar.aoc.solutions.utils.readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.map { str ->
            val stack = Stack<Char>()
            for (c in str) {
                if (c in "<{[(") {
                    stack.add(c)
                } else {
                    val p = ">}])".indexOf(c)
                    val pair = "<{[("[p]
                    if (stack.empty() || stack.pop() != pair) {
                        result += listOf(25137, 1197, 57, 3)[p]
                    }
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Long {
        val result = mutableListOf<Long>()
        input.map { str ->
            val stack = Stack<Char>()
            for (c in str) {
                if (c in "<{[(") {
                    stack.add(c)
                } else {
                    val p = ">}])".indexOf(c)
                    val pair = "<{[("[p]
                    if (stack.empty() || stack.pop() != pair) {
                        return@map 0
                    }
                }
            }
            var r = 0L
            while (!stack.empty()) {
                val c = stack.pop()
                val p = "<{[(".indexOf(c)
                val pair = listOf(4, 3, 2, 1)[p]
                r = r * 5 + pair
            }
            result += r
        }
//        debug(result.sorted())
        return result.sorted().middle()
    }

    val testInput = readInput("Day10_test")
    assertEquals(part1(testInput), 26397)
    assertEquals(part2(testInput), 288957)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
