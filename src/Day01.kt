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
        var sum = input[0] + input[1] + input[2]
        var result = 0
        for (i in 3 until input.size) {
            val newSum = sum + input[i] - input[i - 3]
            if (newSum > sum) {
                result++
            }
            sum = newSum
        }
        return result
    }

    fun read(name: String) = readInput(name).map { it.toInt() }

    // test if implementation meets criteria from the description, like:
    val testInput = read("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
