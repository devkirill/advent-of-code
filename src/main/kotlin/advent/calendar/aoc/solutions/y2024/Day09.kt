package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.repeat

class Day09 : Solution<String>() {
    override fun parse(lines: List<String>) = lines.single()

    fun checksum(data: List<Int?>) = data.mapIndexed { i, p -> i * (p ?: 0).toLong() }.sum()

    override fun part1(input: String): Any {
        val data = mutableListOf<Int?>()
        var id = 0
        input.forEachIndexed { i, c ->
            val n = c.toString().toInt()
            val d = if (i % 2 == 0) {
                id++
            } else {
                null
            }
            repeat(n) {
                data += d
            }
        }
        var i = 0
        var j = data.size - 1
        while (i < j) {
            when {
                data[j] == null -> {
                    data.removeLast()
                    j--
                }

                data[i] == null -> {
                    data[i] = data[j]
                    i++
                    data.removeLast()
                    j--
                }

                else -> i++
            }
        }
        return checksum(data)
    }

    override fun part2(input: String): Any {
        val data = mutableListOf<Pair<Int, Int?>>()
        var id = 0
        input.forEachIndexed { i, c ->
            val n = c.toString().toInt()
            val d = if (i % 2 == 0) {
                id++
            } else {
                null
            }
            if (n != 0) {
                data += n to d
            }
        }
        var J = data.size - 1
        while (J > 0) {
            var i = 0
            var j = J--
            while (i < j) {
                when {
                    data[j].second == null -> {
                        j--
                    }

                    data[i].second == null && data[i].first >= data[j].first -> {
                        if (data[i].first > data[j].first) {
                            if (data[i + 1].second != null) {
                                data.add(i + 1, (data[i].first - data[j].first) to null)
                                j++
                                J++
                            } else {
                                data[i + 1] = (data[i + 1].first + data[i].first - data[j].first) to null
                            }
                            data[i] = data[j]
                        } else {
                            data[i] = data[j]
                        }
                        data[j] = data[j].first to null
                        if (j > 0 && data[j - 1].second == null) j--
                        while (j + 1 < data.size && data[j + 1].second == null) {
                            data[j] = (data[j].first + data[j + 1].first) to null
                            data.removeAt(j + 1)
                            J--
                        }
                        i = 0
                    }

                    else -> i++
                }
            }
        }
        return checksum(data.flatMap { listOf(it.second).repeat(it.first) })
    }
}