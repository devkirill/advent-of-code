package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.middle
import org.springframework.stereotype.Component
import java.util.*

@Component
class Day10 : Solution<List<String>>(2021, 10) {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
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

    override fun part2(input: List<String>): Long {
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
}
