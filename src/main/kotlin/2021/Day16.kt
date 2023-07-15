//import advent.calendar.aoc.solutions.utils.assertEquals
//import advent.calendar.aoc.solutions.utils.readInput
//import java.math.BigInteger
//
//class Parser(val str: String, var shift: Int = 0) {
//    fun next(len: Int): String {
//        val r = str[shift until (shift + len)]
//        shift += len
//        return r
//    }
//
//    fun nextInt(len: Int) = next(len).toInt(2)
//
//    fun getToken(): Token {
//        val v3 = nextInt(3)
//        val t3 = nextInt(3)
//        if (t3 == 4) {
//            var num = ""
//            do {
//                val g = next(5)
//                num += g[1..4]
//            } while (g[0] == '1')
//            return Token(v3, t3, num)
//        } else {
//            val i = nextInt(1)
//            val inner = if (i == 0) {
//                val len = nextInt(15)
//                val p = next(len)
//                val parse = Parser(p)
//                parse.parseAll()
//            } else {
//                val count = nextInt(11)
//                (1..count).map { getToken() }
//            }
//
//            return Token(v3, t3, inner)
//        }
//    }
//
//    fun parseAll(): List<Token> {
//        val result = mutableListOf<Token>()
//        while (shift < str.length - 4) {
//            result += getToken()
//        }
//        return result
//    }
//}
//
//data class Token(val version: Int, val type: Int, val value: Any) {
//    fun version(): Int {
//        var result = version
//        val v = value
//        if (v is List<*>) {
//            val list = v.map { it as Token }
//            result += list.sumOf { it.version() }
//        }
//        return result
//    }
//
//    fun solve(): BigInteger {
//        val v = value
//        val list = if (v is List<*>) v.map { it as Token }.map { it.solve() } else {
//            listOf(BigInteger(v.toString().toLong(2).toString()))
//        }
//        return when (type) {
//            1 -> list.reduce { a, b -> a * b }
//            2 -> list.reduce { a, b -> a.min(b) }
//            3 -> list.reduce { a, b -> a.max(b) }
//            5 -> BigInteger.valueOf(if (list[0] > list[1]) 1 else 0)
//            6 -> BigInteger.valueOf(if (list[0] < list[1]) 1 else 0)
//            7 -> BigInteger.valueOf(if (list[0] == list[1]) 1 else 0)
//            else -> list.sumOf { it }
//        }
//    }
//
//    override fun toString(): String {
//        val v = value
//        val list = if (v is List<*>) v.map { it as Token }.map { it.toString() } else listOf(v.toString())
//        if (type == 4) {
//            return list.map { BigInteger(it.toLong(2).toString()) }.joinToString(",")
//        }
//        if (list.size == 1) {
//            return list.first()
//        }
//        return when (type) {
//            0 -> list.joinToString("+", "(", ")")
//            1 -> list.joinToString("*", "(", ")")
//            2 -> list.joinToString(",", "min(", ")")
//            3 -> list.joinToString(",", "max(", ")")
//            5 -> list.joinToString(">", "(", ")")
//            6 -> list.joinToString("<", "(", ")")
//            7 -> list.joinToString("=", "(", ")")
//            else -> list.joinToString(",")
//        }
//    }
//}
//
//fun main() {
//    fun parts(input: List<String>) = input
//        .map { str ->
//            str.map {
//                val r = it.toString().toInt(16).toString(2)
//                "0".repeat(4 - r.length) + r
//            }.joinToString("")
//        }.map { Parser(it).getToken() }
//        .map { it.version() to it.solve() }
//
//    val testInput = readInput("Day16_test")
//    assertEquals(
//        parts(testInput),
//        listOf(
//            16 to 15,
//            12 to 46,
//            23 to 46,
//            31 to 54,
//            14 to 3,
//            8 to 54,
//            15 to 7,
//            11 to 9,
//            13 to 1,
//            19 to 0,
//            16 to 0,
//            20 to 1
//        )
//    )
//
//    val input = readInput("Day16")
//    println(parts(input).joinToString("\n") { "${it.first} ${it.second}" })
//}
