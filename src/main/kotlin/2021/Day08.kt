import advent.calendar.aoc.solutions.utils.assertEquals
import advent.calendar.aoc.solutions.utils.contains
import advent.calendar.aoc.solutions.utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for (str in input) {
            val s = str.split(" | ").map { it.split(" ") }
            result += s[1].filter { it.length in setOf(2, 3, 4, 7) }.size
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for (str in input) {
            val s = str.split(" | ").map { it.split(" ").map { x -> x.toSet() } }
            val map = mutableMapOf(
                1 to s[0].first { it.size == 2 },
                4 to s[0].first { it.size == 4 },
                7 to s[0].first { it.size == 3 },
                8 to s[0].first { it.size == 7 }
            )
            map[9] = s[0].first { it.size == 6 && map[4]!! in it }
            map[0] = s[0].first { it.size == 6 && map[1]!! in it && it !in map.values }
            map[6] = s[0].first { it.size == 6 && it !in map.values }
            map[3] = s[0].first { it.size == 5 && map[1]!! in it }
            map[5] = s[0].first { it.size == 5 && it in map[6]!!}
            map[2] = s[0].first { it.size == 5 && it !in map.values }
            val num = s[1].map { x -> map.keys.first { map[it] == x } }.joinToString("")
//            debug(num)
            result += num.toInt()
        }
        return result
    }

    val testInput = readInput("Day08_test")
    assertEquals(part1(testInput), 26)
    assertEquals(part2(testInput), 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
