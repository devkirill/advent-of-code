package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution
import advent.calendar.aoc.solutions.utils.SquareMap
import advent.calendar.aoc.solutions.utils.geom.Point
import advent.calendar.aoc.solutions.utils.toSquareMap
import java.util.*

class Day16 : Solution<SquareMap>() {
    override fun parse(lines: List<String>) = lines.toSquareMap()

    override fun part1(input: SquareMap) = solve(input, Position(Point(0, 0), 0))

    override fun part2(input: SquareMap): Int {
        val xx = (0 until input.sizeX).flatMap {
            listOf(
                Position(Point(it, 0), 1),
                Position(Point(it, input.sizeY - 1), 3)
            )
        }
        val yy = (0 until input.sizeY).flatMap {
            listOf(
                Position(Point(0, it), 0),
                Position(Point(input.sizeX - 1, it), 2)
            )
        }
        return (xx + yy).maxOf { solve(input, it) }
    }

    fun solve(input: SquareMap, p: Position): Int {
        val visited = mutableSetOf<Position>()
        val queue = LinkedList(listOf(p))
        while (queue.isNotEmpty()) {
            val cur = queue.pollFirst()
            if (cur !in visited && cur.p in input) {
                visited += cur
                val c = input[cur.p]
                when {
                    c == '|' && cur.dir % 2 == 0 ||
                            c == '-' && cur.dir % 2 == 1 -> {
                        queue += cur.copy(dir = cur.dir + 1).next
                        queue += cur.copy(dir = cur.dir + 3).next
                    }

                    c == '\\' -> {
                        queue += if (cur.dir % 2 == 0) cur.rotateCW().next else cur.rotateCCW().next
                    }

                    c == '/' -> {
                        queue += if (cur.dir % 2 == 1) cur.rotateCW().next else cur.rotateCCW().next
                    }

                    else -> {
                        queue += cur.next
                    }
                }
            }
        }
        return visited.map { it.p }.distinct().size
    }

    data class Position(val p: Point, var dir: Int) {
        init {
            dir %= 4
        }

        val shift = when (dir) {
            0 -> Point(1, 0)
            1 -> Point(0, 1)
            2 -> Point(-1, 0)
            3 -> Point(0, -1)
            else -> TODO()
        }
        val nextPos = p + shift
        val next by lazy { Position(nextPos, dir) }
        fun rotateCW() = Position(p, dir + 1)
        fun rotateCCW() = Position(p, dir + 3)
    }
}
