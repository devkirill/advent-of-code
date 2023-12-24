package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.PairTuple
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.geom.nearby4
import advent.calendar.aoc.solutions.utils.heapPop
import advent.calendar.aoc.solutions.utils.heapPush
import kotlin.collections.List
import kotlin.collections.contains
import kotlin.collections.flatMap
import kotlin.collections.indices
import kotlin.collections.isNotEmpty
import kotlin.collections.last
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.toMap
import kotlin.collections.toMutableMap

class Day15 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
        var result = 0
        val g = input.indices.flatMap { y ->
            (0 until input[y].length).map { x ->
                Point(x, y) to input[y][x].toString().toInt()
            }
        }.toMap()
        val from = Point(0, 0)
        val to = Point(input.last().length - 1, input.size - 1)

        val map = mutableMapOf(from to 0)
        val queue = mutableListOf(PairTuple(0, from))
        while (queue.isNotEmpty()) {
            val (prev, next) = queue.heapPop()
            for (p in next.nearby4()) {
                if (p !in map && p in g) {
                    map[p] = prev + g[p]!!
                    queue.heapPush(PairTuple(map[p]!!, p))
                }
            }
        }

        return map[to]!!
    }

    override fun part2(input: List<String>): Int {
        val g = input.indices.flatMap { y ->
            (0 until input[y].length).map { x ->
                Point(x, y) to input[y][x].toString().toInt()
            }
        }.toMap().toMutableMap()

        val from = Point(0, 0)
        val last = Point(input.last().length, input.size)
        var to = Point(input.last().length - 1, input.size - 1)

        for (x in from.x until last.x) {
            for (y in from.y until last.y) {
                val p = g[Point(x, y)]!!
                for (t in 0 until 25) {
                    val a = Point(last.x, 0) * (t / 5) + Point(0, last.y) * (t % 5)
                    val v = (p + (t / 5 + t % 5) - 1) % 9 + 1
                    g[a + Point(x, y)] = v
                }

            }
        }

        to = last * 5 - Point(1, 1)

//        for (x in from.x until to.x) {
//            debug((from.y..to.y).map { Point(it, x) }.map { g[it]!! }.joinToString(""))
//        }

        val map = mutableMapOf(from to 0)//g[from]!!)
        val queue = mutableListOf(PairTuple(0, from))
        while (queue.isNotEmpty()) {
            val (prev, next) = queue.heapPop()
            for (p in next.nearby4()) {
                if (p !in map && p in g) {
                    map[p] = prev + g[p]!!
                    queue.heapPush(PairTuple(map[p]!!, p))
                }
            }
        }

        return map[to]!!
    }
}
