package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import java.math.BigInteger

class Day11 : Solution<List<BigInteger>>() {
    override fun parse(lines: List<String>) = lines.single().split(" ").map { it.toBigInteger() }

    val cache = mutableMapOf<Pair<BigInteger, Int>, BigInteger>()

    fun findResult(num: BigInteger, after: Int): BigInteger {
        if (after == 0) return BigInteger.ONE
        if (num to after !in cache) {
            val steps = when {
                num == BigInteger.ZERO -> findResult(BigInteger.ONE, after - 1)
                num.toString().length % 2 == 0 -> {
                    val s = num.toString()
                    findResult(
                        s.substring(0, s.length / 2).toBigInteger(),
                        after - 1
                    ) + findResult(s.substring(s.length / 2).toBigInteger(), after - 1)
                }

                else -> findResult(num * BigInteger.valueOf(2024), after - 1)
            }
            cache[num to after] = steps
        }
        return cache[num to after]!!
    }

    override fun part1(input: List<BigInteger>): Any {
        return input.sumOf { findResult(it, 25) }.also { it }
    }

    override fun part2(input: List<BigInteger>): Any {
        return input.sumOf { findResult(it, 75) }.also { it }
    }
}