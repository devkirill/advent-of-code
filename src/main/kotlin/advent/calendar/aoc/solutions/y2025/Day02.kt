package advent.calendar.aoc.solutions.y2025

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.toPair
import java.math.BigInteger

class Day02 : Solution<List<Pair<String, String>>>() {
    override fun parse(lines: List<String>) =
        lines.map { l ->
            l.split(",").map { pair ->
                pair.split("-").toPair
            }
        }.flatten()

    fun String.numSplit(len: Int): Pair<BigInteger, BigInteger> {
        return BigInteger(dropLast(len).ifEmpty({ "0" })) to BigInteger(takeLast(len).ifEmpty({ "0" }))
    }

    fun BigInteger.concat(): BigInteger {
        return BigInteger(toString() + toString())
    }

    override fun part1(input: List<Pair<String, String>>): Any {
        return input.map { (a, b) ->
            var result = BigInteger.ZERO
//        if (a.length == b.length && a.length % 2 == 1) return@map result
            val A = BigInteger(a)
            val B = BigInteger(b)
            val (a1, a2) = a.numSplit((a.length + 1) / 2)
            val (b1, b2) = b.numSplit((a.length + 1) / 2)
            val l = (if (a1 >= a2) a1 else (a1 + BigInteger.ONE))
            val r = (if (b1 <= b2) b1 else (b1 - BigInteger.ONE))
//            Logger.warn("$a $b $l $r", newLine = true)
            var i = l
            while (i <= r) {
                val r = i.concat()
                if (r in A..B) {
//                    print("${r},")
                    result += r
                }
                i++
            }
//            println()
            result
        }
    }
}
