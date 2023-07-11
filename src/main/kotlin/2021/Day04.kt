fun main() {
    fun part1(input: List<String>): Int {
        val nums = input[0].split(",").map(String::toInt)
        val numMap = nums.indices.map { nums[it] to it }

        return (0 until input.size / 6).map { board ->
            val matrix = Matrix(0..4, 0..4)
            var resultStep = -1
            var result = 0
            val m1 = (0..4).map {
                input[board * 6 + it + 2].split(" ")
                    .filter { i -> i.isNotBlank() }
                    .map(String::toInt)
            }
            val m2 = (0..4).map { x -> (0..4).map { m1[it][x] } }
            val m = m1 + m2
            val marked = mutableSetOf<Int>()
            for (i in nums.indices) {
                val n = nums[i]
                marked += n
                val match = m.filter { l -> l.all { it in marked } }
                if (match.isNotEmpty()) {
                    resultStep = i
                    result = matrix.indices.filter { m1[it] !in marked }.sumOf { m1[it] } * n
                    break
                }
            }
//            debug(resultStep, result)
            resultStep to result
        }.minByOrNull { it.first }!!.second
    }

    fun part2(input: List<String>): Int {
        val nums = input[0].split(",").map(String::toInt)
        val numMap = nums.indices.map { nums[it] to it }

        return (0 until input.size / 6).map { board ->
            val matrix = Matrix(0..4, 0..4)
            var resultStep = -1
            var result = 0
            val m1 = (0..4).map {
                input[board * 6 + it + 2].split(" ")
                    .filter { i -> i.isNotBlank() }
                    .map(String::toInt)
            }
            val m2 = (0..4).map { x -> (0..4).map { m1[it][x] } }
            val m = m1 + m2
            val marked = mutableSetOf<Int>()
            for (i in nums.indices) {
                val n = nums[i]
                marked += n
                val match = m.filter { l -> l.all { it in marked } }
                if (match.isNotEmpty()) {
                    resultStep = i
                    result = matrix.indices.filter { m1[it] !in marked }.sumOf { m1[it] } * n
                    break
                }
            }
//            debug(resultStep, result)
            resultStep to result
        }.maxByOrNull { it.first }!!.second
    }

    val testInput = readInput("Day04_test")
    assertEquals(part1(testInput), 4512)
    assertEquals(part2(testInput), 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}