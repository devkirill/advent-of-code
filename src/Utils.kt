import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    val file = File("src\\main\\resources", "$name.txt")
    try {
        return file.readLines()
    } catch (e: Throwable) {
        println(file.absolutePath)
        throw e
    }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun assertEquals(actual: Any?, expected: Any?) {
    if (actual.toString() != expected.toString()) {
        throw IllegalStateException("$actual != $expected")
    }
}

fun debug(vararg str: Any?) = System.err.println(str.toList().joinToString(" ") { "$it" })

operator fun <T> Set<T>.contains(other: Collection<T>): Boolean {
    return other.all { it in this }
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(p: Point) = Point(x + p.x, y + p.y)
    operator fun minus(p: Point) = Point(x + p.x, y + p.y)
    operator fun times(p: Point) = Point(x * p.x, y * p.y)
}

fun Point.nearby4() = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1)).map { this + it }
fun Point.nearby8() = (-1..1)
    .flatMap { x -> (-1..1).map { y -> Point(x, y) } }
    .filter { it.x != 0 || it.y != 0 }
    .map { this + it }

fun <T> List<T>.middle() = this[size / 2]

data class Matrix(val x: IntRange, val y: IntRange) {
    constructor(fromX: Int, toX: Int, fromY: Int, toY: Int) : this(fromX..toX, fromY..toY)

    val indices: Set<Point> by lazy { x.flatMap { x -> y.map { y -> Point(x, y) } }.toSet() }

    operator fun contains(p: Point) = p in indices
}

operator fun List<String>.get(p: Point) = this[p.x][p.y]