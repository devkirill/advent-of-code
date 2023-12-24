package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.SquareMapCyclic
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.nearby4
import advent.calendar.aoc.solutions.utils.toSquareMap
import advent.calendar.aoc.solutions.utils.tryPredictSequence
import java.util.*
import kotlin.collections.List
import kotlin.collections.asSequence
import kotlin.collections.contains
import kotlin.collections.count
import kotlin.collections.filter
import kotlin.collections.first
import kotlin.collections.isNotEmpty
import kotlin.collections.mutableMapOf
import kotlin.collections.plusAssign
import kotlin.collections.set

class Day21 : Solution<SquareMapCyclic>() {
    override fun parse(lines: List<String>) = lines.toSquareMap().cyclic()

    override fun part1(input: SquareMapCyclic) = solve(input, 64)

    override fun part2(input: SquareMapCyclic) = solve(input, 26501365)

    fun solve(input: SquareMapCyclic, index: Int): Long {
        val start = input.keys.first { input[it] == 'S' }
        val map = mutableMapOf<Point, Int>()
        map[start] = 0
        val queue: Queue<Point> = LinkedList()
        queue += start
        fun solve(idx: Int): Long {
            while (queue.isNotEmpty()) {
                val cur = queue.remove()
                for (next in cur.nearby4().filter { input[it] != '#' }) {
                    if (next !in map) {
                        map[next] = map[cur]!! + 1
                        queue += next
                    }
                }
                if (map[cur]!! > idx) {
                    return map.count { it.value <= idx && it.value % 2 == idx % 2 }.toLong()
                }
            }
            return 0
        }

        val size = input.sizeX * 2
        return (0..2000)
            .filter { it % size == index % size }
            .apply { println(this) }
            .asSequence()
            .map { solve(it) }
            .tryPredictSequence(index / size)
    }
}
