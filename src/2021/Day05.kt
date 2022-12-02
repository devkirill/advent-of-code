import java.lang.Integer.max
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map { it.split(" -> ").toIntPoints(",") }
        val map = mutableMapOf<Point, Int>()
        for (l in lines) {
            val from = l[0]
            val v = l[1] - from
            if (v.x == 0 || v.y == 0) {
                val max = max(abs(v.x), abs(v.y))
                for (i in 0..max) {
                    val p = from + (v / max * i)
                    map[p] = (map[p] ?: 0) + 1
                }
            }
        }
        return map.values.filter { it > 1 }.size
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { it.split(" -> ").toIntPoints(",") }
        val map = mutableMapOf<Point, Int>()
        for (l in lines) {
            val from = l[0]
            val v = l[1] - from

            val max = max(abs(v.x), abs(v.y))
            for (i in 0..max) {
                val p = from + (v / max * i)
                map[p] = (map[p] ?: 0) + 1
            }
        }
        return map.values.filter { it > 1 }.size
    }

    val testInput = readInput("Day05_test")
    assertEquals(part1(testInput), 5)
    assertEquals(part2(testInput), 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
