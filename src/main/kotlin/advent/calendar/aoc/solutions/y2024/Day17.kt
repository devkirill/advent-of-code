package advent.calendar.aoc.solutions.y2024

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.splitEmptyLine
import advent.calendar.aoc.solutions.y2024.Day17.Data

class Day17 : Solution<Data>() {
    override fun parse(lines: List<String>) = lines.splitEmptyLine().let { (a, b) ->
        Data(
            a.map { it.substringAfter(": ").toLong() },
            b.map { it.substringAfter(": ").split(",").map { it.toInt() } }.reduce { a, b -> a + b }
        )
    }

    fun shr(a: Long, b: Long): Long {
        var a = a
        var b = b
        while (b > 0L && a > 0L) {
            b--
            a /= 2
        }
        return a
    }

    fun calc(input: Data): String {
        val reg = input.reg.toMutableList()
        val program = input.program.chunked(2)
        val output = mutableListOf<Long>()
        var idx = 0
        while (idx < program.size) {
            val (command, op) = program[idx]
            val cop = when (op) {
                in 0..3 -> op.toLong()
                in 4..6 -> reg[op - 4]
                else -> 0
            }
            when (command) {
                0 -> reg[0] = shr(reg[0], cop)
                6 -> reg[1] = shr(reg[0], cop)
                7 -> reg[2] = shr(reg[0], cop)
                1 -> reg[1] = reg[1] xor op.toLong()
                2 -> reg[1] = cop and 7
                3 -> if (reg[0] != 0L) idx = cop.toInt() - 1
                4 -> reg[1] = reg[1] xor reg[2]
                5 -> output += cop and 7
            }
            idx++
        }
        return output.joinToString(",")// + reg.toString()
    }

    override fun part1(input: Data) = calc(input)

//    override fun part2(input: Data): Any {
//        fun calc(num: List<Long>) = calc(input.copy(reg = listOf(num.reduce{a,b -> a * 8 + b}, 0, 0)))
//
//        val target = input.program.joinToString(",")
//
//        fun find(l: List<Long>, r: List<Long>): List<Long>? {
//            for (suffix in (0L until 7).shuffled()) {
//                val c = l + listOf(suffix) + r
//                val c2 = calc(c)
//                if ((target.startsWith(c2) || target.endsWith(c2)) && c2.length == num.size * 2 + 1) {
//                    println("$c $c2")
//                    val r = find(c)
//                    if (r != null) return r
//                }
//            }
//            for (prefix in (0L until 7).shuffled()) {
//                val c = listOf(prefix) + num
//                val c2 = calc(c)
//                if (c2 == target) return c
//                if ((target.startsWith(c2) || target.endsWith(c2)) && c2.length == num.size * 2 + 1) {
//                    println("$c $c2")
//                    val r = find(c)
//                    if (r != null) return r
//                }
//            }
//            return null
//        }
//
////        if (target != "0,3,5,4,3,0") return 0
//
//        return find(listOf())?.reduce{a,b -> a * 8 + b} ?: 0
//    }

    data class Data(
        val reg: List<Long>,
        val program: List<Int>
    )
}
