fun main() {
    fun part1(input: List<String>): Int {
        val l = input[0].split(",").map(String::toInt)
        var pos = (0..8).map { x -> l.filter { it == x }.size }
        repeat(80) {
            pos = (0..7).map { (if (it == 6) pos[0] else 0) + pos[it + 1] } + pos[0]
        }
        return pos.sum()
    }

    fun part2(input: List<String>): Long {
        val l = input[0].split(",").map(String::toInt)
        var pos = (0..8).map { x -> l.filter { it == x }.size.toLong() }
        repeat(256) {
            pos = (0..7).map { (if (it == 6) pos[0] else 0) + pos[it + 1] } + pos[0]
        }
        return pos.sum()
    }

    val testInput = readInput("Day06_test")
    assertEquals(part1(testInput), 5934)
    assertEquals(part2(testInput), 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
