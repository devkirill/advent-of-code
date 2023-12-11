package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.PairTuple
import advent.calendar.aoc.solutions.utils.Point
import advent.calendar.aoc.solutions.utils.heapPop
import advent.calendar.aoc.solutions.utils.heapPush
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.all
import kotlin.collections.distinct
import kotlin.collections.filter
import kotlin.collections.isNotEmpty
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.plus
import kotlin.collections.plusAssign
import kotlin.collections.set
import kotlin.collections.toList
import kotlin.collections.toMutableList
import kotlin.math.abs

class Day23 : Solution<List<Int>>() {
    override fun parse(lines: List<String>): List<Int> {
        return (0..10).map { 0 } +
                lines[2].split("#").filter { it.isNotBlank() }.map { it[0] - 'A' + 1 } +
                lines[3].split("#").filter { it.isNotBlank() }.map { it[0] - 'A' + 1 }
    }

    override fun parse2(lines: List<String>): List<Int> {
        return (0..10).map { 0 } +
                lines[2].split("#").filter { it.isNotBlank() }.map { it[0] - 'A' + 1 } +
                listOf(4, 3, 2, 1) +
                listOf(4, 2, 1, 3) +
                lines[3].split("#").filter { it.isNotBlank() }.map { it[0] - 'A' + 1 }
    }

    val energy = listOf(0, 1, 10, 100, 1000)
    val l = listOf(0, 1, 3, 5, 7, 9, 10)


    operator fun MutableList<Int>.set(r: Int, c: Int, value: Int) {
        when (r) {
            0 -> this[c] = value
            else -> this[7 + r * 4 + c] = value
        }
    }

    operator fun List<Int>.get(r: Int, c: Int): Int {
        return when (r) {
            0 -> this[c]
            else -> this[7 + r * 4 + c]
        }
    }

    fun List<Int>.move(from: Pair<Int, Int>, to: Pair<Int, Int>): List<Int> {
        val result = toMutableList()
        result[to.first, to.second] = result[from.first, from.second]
        result[from.first, from.second] = 0
        return result
    }

    fun List<Int>.tryMove(from: Pair<Int, Int>): List<PairTuple<Int, List<Int>>> {
        val (r, c) = from
        val e = energy[this[r, c]]
        val result = mutableListOf<PairTuple<Int, List<Int>>>()

        fun check(a: Int, b: Int): Boolean {
            val range = if (a < b) a..b else b..a
            return range.all { this[0, it] <= 0 }
        }
        if (r > 0) {
            for (i in l) {
                val j = (c + 1) * 2
                if (check(i, j)) {
                    result += PairTuple((r + abs(i - j)) * e, this.move(from, 0 to i))
                }
            }
        } else {
            val t = this[r, c] - 1
            if (this[1, t] in listOf(0, this[r, c]) && this[2, t] in listOf(0, this[r, c])) {
                val j = (t + 1) * 2
                val range = if (j < c) j until c else (c + 1)..j
                if (range.all { this[0, it] <= 0 }) {
                    result += if (this[2, t] == 0) {
                        PairTuple(e * (range.toList().size + 2), this.move(from, 2 to t))
                    } else {
                        PairTuple(e * (range.toList().size + 1), this.move(from, 1 to t))
                    }
                }
            }
        }
        return result
    }

    fun List<Point>.apply(dir: String, pos: Int): List<Point> {
        return if (dir == "x") {
            this.map { it.by { x = pos - abs(pos - x) } }.distinct()
        } else {
            this.map { it.by { y = pos - abs(pos - y) } }.distinct()
        }
    }

    override fun part1(input: List<Int>): Int {
        val best = mutableMapOf(input to 0)
        val path = mutableMapOf<List<Int>, List<Int>>()

        var queue = mutableListOf(PairTuple(0, input))
        while (queue.isNotEmpty()) {
            val cur = queue.heapPop()
            val (points, table) = cur

            val result = mutableListOf<PairTuple<Int, List<Int>>>()
            for (i in l) {
                if (table[0, i] > 0) {
                    result += table.tryMove(0 to i)
                }
            }
            for (i in 0 until 4) {
                if (table[1, i] > 0) {
                    result += table.tryMove(1 to i)
                } else if (table[2, i] > 0) {
                    result += table.tryMove(2 to i)
                }
            }
            for (pt in result.map { (p, t) -> PairTuple(p + points, t) }) {
                val (p, t) = pt
                if ((best[t] ?: (p + 1)) > p) {
                    best[t] = p
                    path[t] = table
                    queue.heapPush(pt)
                }
            }
        }

        val end = (0..10).map { 0 } + listOf(1, 2, 3, 4, 1, 2, 3, 4)
        var cur: List<Int>? = end
        while (cur != null) {
            cur = path[cur]
        }

        return best[end] ?: 0
    }

    override fun part2(input: List<Int>): Int {
        val best = mutableMapOf(input to 0)
        val path = mutableMapOf<List<Int>, List<Int>>()

        var queue = mutableListOf(PairTuple(0, input))
        val end = (0..10).map { 0 } + listOf(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
        while (queue.isNotEmpty()) {
            val cur = queue.heapPop()
            val (points, table) = cur

            if (best[table] != points) {
                continue
            }

            if (end == table) {
                return points
            }

            val result = mutableListOf<PairTuple<Int, List<Int>>>()
            for (i in l) {
                if (table[0, i] > 0) {
                    result += table.tryMove(0 to i)
                }
            }
            for (i in 0 until 4) {
                var p = 1
                while (p <= 4 && table[p, i] == 0) p++
                if (p <= 4) {
                    result += table.tryMove(p to i)
                }
            }
            for (pt in result.map { (p, t) -> PairTuple(p + points, t) }) {
                val (p, t) = pt
                if ((best[t] ?: (p + 1)) > p) {
                    best[t] = p
                    path[t] = table
                    queue.heapPush(pt)
                }
            }
        }

        return best[end] ?: 0
    }
}
