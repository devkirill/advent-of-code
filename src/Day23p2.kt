import kotlin.math.abs

fun main() {
    val energy = listOf(0, 1, 10, 100, 1000)
    val l = listOf(0, 1, 3, 5, 7, 9, 10)

    fun parse(input: List<String>): List<Int> {
        return (0..10).map { 0 } +
                input[2].split("#").filter { it.isNotBlank() }.map { it[0] - 'A' + 1 } +
                listOf(4, 3, 2, 1) +
                listOf(4, 2, 1, 3) +
                input[3].split("#").filter { it.isNotBlank() }.map { it[0] - 'A' + 1 }
    }

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
            if ((1..4).all { this[it, t] in listOf(0, this[r, c]) }) {
                val j = (t + 1) * 2
                val range = if (j < c) j until c else (c + 1)..j
                if (range.all { this[0, it] <= 0 }) {
                    var p = 4
                    while (this[p, t] != 0) {
                        p--
                    }
                    result += PairTuple(e * (range.toList().size + p), this.move(from, p to t))
                }
            }
        }
        return result
    }


    fun part2(input: List<String>): Int {
        val init = parse(input)
        val best = mutableMapOf(init to 0)
        val path = mutableMapOf<List<Int>, List<Int>>()

        var queue = mutableListOf(PairTuple(0, init))
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

        val end = (0..10).map { 0 } + listOf(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
        var cur: List<Int>? = end
        while (cur != null) {

            println((0..10).map { cur!![0, it] }.joinToString(""))
            for (r in 1..4) {
                println((0 until 4).map { cur!![r, it] }.joinToString(" ", "  "))
            }
            println(best[cur])
            cur = path[cur]
        }
        println()

        return best[end] ?: 0
    }

    val testInput = readInput("Day23_test")
    assertEquals(part2(testInput), 44169)

    val input = readInput("Day23")
    println(part2(input))
}