//fun main() {
//    fun part1(input: List<String>): Long {
//        var x = 0L
//        var y = 0L
//        for (i in input) {
//            val s = i.split(" ")
//            val c = s[1].toInt()
//            when (s[0]) {
//                "forward" -> x += c
//                "up" -> y -= c
//                "down" -> y += c
//            }
//        }
//        return x * y
//    }
//
//    fun part2(input: List<String>): Long {
//        var x = 0L
//        var y = 0L
//        var z = 0L
//        for (i in input) {
//            val s = i.split(" ")
//            val c = s[1].toInt()
//            when (s[0]) {
//                "forward" -> {
//                    x += c
//                    y += c * z
//                }
//                "up" -> z -= c
//                "down" -> z += c
//            }
////            debug(x, y, z)
//        }
//        return x * y
//    }
//
//    val testInput = readInput("Day02_test")
//    assertEquals(part1(testInput), 150)
//    assertEquals(part2(testInput), 900)
//
//    val input = readInput("Day02")
//    println(part1(input))
//    println(part2(input))
//}
