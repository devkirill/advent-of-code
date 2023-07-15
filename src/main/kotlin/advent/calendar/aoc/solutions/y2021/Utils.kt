package advent.calendar.aoc.solutions.y2021

import advent.calendar.aoc.solutions.utils.Point

operator fun List<String>.get(p: Point) = this[p.x][p.y]
operator fun <T> List<List<T>>.get(p: Point) = this[p.x][p.y]
