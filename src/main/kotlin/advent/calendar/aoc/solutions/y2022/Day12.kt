//package advent.calendar.aoc.solutions.y2022
//
//import advent.calendar.aoc.Solution
//import advent.calendar.aoc.solutions.utils.*
//import org.springframework.stereotype.Component
//import java.util.LinkedList
//import kotlin.math.abs
//
//@Component
//class Day12 : Solution<Day12.WorldMap>() {
//    override fun parse(lines: List<String>): WorldMap {
//        var s = Point(0, 0)
//        var e = Point(0, 0)
//        var map = mutableMapOf<Point, Int>()
//        for (y in lines.indices) {
//            for (x in lines[0].indices) {
//                val p = Point(x,y)
//                val c = lines[y][x]
//                val w = when (c) {
//                    in 'a'..'z' -> c - 'a'
//                    'E' -> {
//                        e = p
//                        'z'-'a'
//                    }
//                    'S' -> {
//                        s = p
//                        0
//                    }
//                    else -> TODO()
//                }
//                map[p]=w
//            }
//        }
//        return WorldMap(s, e, map)
//    }
//
//    override fun part1(input: WorldMap): Int {
//        val result = mutableMapOf(input.s to 0)
//        val queue = LinkedList<Point>()
//        queue.addAll()result.keys
//        while (queue.isNotEmpty())
//    }
//
//    data class WorldMap(val s: Point, val e: Point, val g: Map<Point, Int>)
//}
