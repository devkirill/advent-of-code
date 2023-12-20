package advent.calendar.aoc.solutions.utils

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
    val next by lazy { copy(p = nextPos) }
    fun rotateCW() = Position(p, dir + 1)
    fun rotateCCW() = Position(p, dir + 3)
}
