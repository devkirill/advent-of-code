package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.Matrix
import org.springframework.stereotype.Component

@Component
class Day04 : Solution<List<String>>() {
    override fun parse(lines: List<String>) = lines

    override fun part1(input: List<String>): Int {
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

    override fun part2(input: List<String>): Int {
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
}
