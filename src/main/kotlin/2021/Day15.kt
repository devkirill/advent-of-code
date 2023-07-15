import advent.calendar.aoc.solutions.utils.*

fun main() {
    fun part1(input: List<String>): Int {
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

    fun part2(input: List<String>): Int {
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

    val testInput = readInput("Day15_test")
    assertEquals(part1(testInput), 40)
    assertEquals(part2(testInput), 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
