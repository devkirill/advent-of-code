fun main() {
    fun prep(input: List<String>): List<Int> {
        var result = listOf<Int>()
        var cur = 0
        input.forEach {
            if (it == "") {
                result = result + cur
                cur = 0
            } else {
                cur += it.toInt()
            }
        }
        result = result + cur
        return result
    }

    fun part1(input: List<Int>): Int {
        return input.maxOf { it }
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum()
    }

    fun read(name: String) = prep(readInput(name))

    // test if implementation meets criteria from the description, like:
    val testInput = read("Day01_test")
    assertEquals(part1(testInput), 24000)
    assertEquals(part2(testInput), 45000)

    val input = read("Day01")
    println(part1(input))
    println(part2(input))
}
