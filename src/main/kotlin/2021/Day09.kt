fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for (x in input.indices) {
            for (y in input[x].indices) {
                var a = true
                if (x > 0 && input[x][y] >= input[x - 1][y]) a = false
                if (y > 0 && input[x][y] >= input[x][y - 1]) a = false
                if (x + 1 < input.size && input[x][y] >= input[x + 1][y]) a = false
                if (y + 1 < input[x].length && input[x][y] >= input[x][y + 1]) a = false
                if (a) {
                    result += input[x][y].toString().toInt() + 1
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val lower = mutableListOf<Point>()
        for (x in input.indices) {
            for (y in input[x].indices) {
                var a = true
                if (x > 0 && input[x][y] >= input[x - 1][y]) a = false
                if (y > 0 && input[x][y] >= input[x][y - 1]) a = false
                if (x + 1 < input.size && input[x][y] >= input[x + 1][y]) a = false
                if (y + 1 < input[x].length && input[x][y] >= input[x][y + 1]) a = false
                if (a) {
                    lower += Point(x, y)
                }
            }
        }
        val visited = mutableSetOf<Point>()
        val total = mutableListOf<Int>()
        for (p in lower) {
            if (p !in visited) {
                var count = 0
                var queue = listOf(p)
                while (queue.isNotEmpty()) {
                    visited += queue
                    count += queue.size
                    val next = queue.flatMap { it.nearby4() }
                        .asSequence()
                        .filter { it.x in input.indices }
                        .filter { it.y in input[0].indices }
                        .filter { it !in visited }
                        .filter { input[it.x][it.y] < '9' }
                        .distinct()
                        .toList()
                    queue = next
                }
                total += count
            }
        }
        return total.sortedDescending().take(3).reduce { x, y -> x * y }
    }

    val testInput = readInput("Day09_test")
    assertEquals(part1(testInput), 15)
    assertEquals(part2(testInput), 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
