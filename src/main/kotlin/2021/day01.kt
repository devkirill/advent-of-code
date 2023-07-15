fun main() {
    fun part1(input: List<Int>): Int {
        var result = 0
        for (i in 1 until input.size) {
            if (input[i] > input[i - 1]) {
                result++
            }
        }
        return result
    }

    fun part2(input: List<Int>): Int {
        var result = 0
        for (i in 3 until input.size) {
            if (input[i] > input[i - 3]) {
                result++
            }
        }
        return result
    }

    fun read(name: String) = readInput(name).map { it.toInt() }

    // test if implementation meets criteria from the description, like:
    val testInput = read("Day01_test")
    assertEquals(part1(testInput), 7)
    assertEquals(part2(testInput), 5)

    val input = read("Day01")
    println(part1(input))
    println(part2(input))
}
