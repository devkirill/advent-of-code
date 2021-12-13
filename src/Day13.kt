import kotlin.math.abs

fun main() {
    fun List<Point>.apply(dir: String, pos: Int): List<Point> {
        return if (dir == "x") {
            this.map { it.by { x = pos - abs(pos - x) } }.distinct()
        } else {
            this.map { it.by { y = pos - abs(pos - y) } }.distinct()
        }
    }

    fun part1(input: List<String>): Int {
        var points = input.filter { "," in it }.toIntPoints(",")
        val folds = input.filter { "fold" in it }.map { it.replace("fold along ", "").splitPair("=") }

        for (fold in folds.take(1)) {
            points = points.apply(fold.first, fold.second.toInt())
        }
        return points.size
    }

    fun part2(input: List<String>): Int {
        var points = input.filter { "," in it }.toIntPoints(",")
        val folds = input.filter { "fold" in it }.map { it.replace("fold along ", "").splitPair("=") }

        for (fold in folds) {
            points = points.apply(fold.first, fold.second.toInt())
        }
        for (y in points.minOf { it.y }..points.maxOf { it.y }) {
            println((points.minOf { it.x }..points.maxOf { it.x })
                .joinToString("") { if (Point(it, y) in points) "#" else " " })
        }
        return points.size
    }

    val testInput = readInput("Day13_test")
    assertEquals(part1(testInput), 17)
    assertEquals(part2(testInput), 16)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
