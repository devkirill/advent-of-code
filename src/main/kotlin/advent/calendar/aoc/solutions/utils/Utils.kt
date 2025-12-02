package advent.calendar.aoc.solutions.utils

import advent.calendar.aoc.solutions.utils.geom.Point
import java.io.File
import java.lang.Integer.min
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    val file = File("src/resources", "$name.txt")
    try {
        return file.readLines()
    } catch (e: Throwable) {
        println(file.absolutePath)
        throw e
    }
}

fun <T> runSolve(day: Int, testSolve: T, solve: (List<String>) -> T) {
    val testInput = readInput("Day${day}_test")
    assertEquals(solve(testInput), testSolve)

    val input = readInput("Day$day")
    println(solve(input))
}

fun <T> runSolveCount(day: Int, count: Int, testSolve: T, solve: (List<String>, Int) -> T) {
    val testInput = readInput("Day${day}_test")
    assertEquals(solve(testInput, count), testSolve)

    val input = readInput("Day$day")
    println(solve(input, count))
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

//region Point


//endregion

fun <T> List<T>.middle() = this[size / 2]

data class Matrix(val x: IntRange, val y: IntRange) {
    constructor(fromX: Int, toX: Int, fromY: Int, toY: Int) : this(fromX..toX, fromY..toY)

    val indices: Set<Point> by lazy { x.flatMap { x -> y.map { y -> Point(x, y) } }.toSet() }

    operator fun contains(p: Point) = p in indices
}

operator fun List<String>.get(p: Point) = this[p.x][p.y]
operator fun <T> List<List<T>>.get(p: Point) = this[p.x][p.y]

fun List<String>.toInt() = this.map { it.toInt() }
fun List<Int>.toPoint() = Point(this[0], this[1])
val <T> List<T>.toPair get() = Pair(first(), last())
fun List<String>.toIntPoint() = this.toInt().toPoint()
fun List<String>.toIntPoints(delimiter: String) = this.map { it.split(delimiter).toIntPoint() }
fun String.splitPair(delimiter: String): Pair<String, String> {
    val a = this.split(delimiter)
    return Pair(a[0], a[1])
}

operator fun String.get(range: IntRange) = this.substring(range)
operator fun <T> List<T>.get(range: IntRange) = this.subList(range.first, range.last + 1)

object Levenshtein {
    fun <T> distance(l: List<T>, r: List<T>): Int {
        val lLength = l.size
        val rLength = r.size

        var cost = Array(lLength + 1) { it }
        var newCost = Array(lLength + 1) { 0 }

        for (i in 1..rLength) {
            newCost[0] = i

            for (j in 1..lLength) {
                val match = if (l[j - 1] == r[i - 1]) 0 else 1

                val costReplace = cost[j - 1] + match
                val costInsert = cost[j] + 1
                val costDelete = newCost[j - 1] + 1

                newCost[j] = min(min(costInsert, costDelete), costReplace)
            }

            val swap = cost
            cost = newCost
            newCost = swap
        }

        return cost[lLength]
    }

    fun distance(l: String, r: String): Int = distance(l.toList(), r.toList())
}

fun <T> MutableList<T>.pop(): T {
    val last = this.last()
    this.removeAt(this.size - 1)
    return last
}

fun <T> MutableList<T>.push(e: T) {
    this += e
}

//region heap

fun <T : Comparable<T>> MutableList<T>.heapify(p: Int) {
    var i = p
    while (true) {
        val left = 2 * i + 1
        val right = 2 * i + 2
        var best = i
        if (left < size && this[left] < this[best]) best = left
        if (right < size && this[right] < this[best]) best = right
        if (best == i) {
            break
        }
        val temp = this[i]
        this[i] = this[best]
        this[best] = temp
        i = best
    }
}

// https://habr.com/ru/post/112222/
fun <T : Comparable<T>> MutableList<T>.heapPush(element: T) {
    add(element)
    var i = this.size - 1
    var parent = (i - 1) / 2

    while (i > 0 && this[parent] > this[i]) {
        val temp = this[i]
        this[i] = this[parent]
        this[parent] = temp

        i = parent
        parent = (i - 1) / 2
    }
}

fun <T : Comparable<T>> MutableList<T>.heapPop(): T {
    val result = this[0]
    this[0] = this.last()
    this.removeAt(size - 1)
    heapify(0)
    return result
}

//endregion

//region tuple

data class PairTuple<A : Comparable<A>, B>(val first: A, val second: B) : Comparable<PairTuple<A, B>> {
    override fun compareTo(other: PairTuple<A, B>): Int {
        return first.compareTo(other.first)
    }
}

fun <A : Comparable<A>, B> tuple(first: A, second: B) = PairTuple(first, second)

fun <A : Comparable<A>, B> tuple(pair: Pair<A, B>) = tuple(pair.first, pair.second)

//endregion

//fun minMax(list: List<Int>) = (list.minOrNull() ?: 0) to (list.maxOrNull() ?: 0)

fun Collection<Int>.range() = (minOrNull() ?: 0)..(maxOrNull() ?: 0)

fun IntRange.extend(size: Int = 1) = (first - size)..(last + size)

fun <A, B> List<Pair<A, B>>.toMutableMap() = toMap().toMutableMap()

fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Int, b: Int) = a / gcd(a, b) * b

fun lcm(a: Long, b: Long) = a / gcd(a, b) * b

fun parseInt(s: String) = s.replace(Regex("\\D"), "").toInt()

fun pow(a: Int, p: Int): Int {
    if (p < 0) {
        return 0
    }
    var x = 1
    repeat(p) {
        x *= a
    }
    return x
}

operator fun BigInteger.plus(a: Int) = this + a.toBigInteger()
operator fun BigInteger.minus(a: Int) = this - a.toBigInteger()
operator fun BigInteger.div(a: Int) = this / a.toBigInteger()
operator fun BigInteger.rem(a: Int) = this % a.toBigInteger()

operator fun List<Int>.times(a: Int) = map { it * a }

fun List<String>.splitEmptyLine() = this.joinToString("\n").split("\n\n").map { it.split("\n") }

fun List<String>.toSquareMap(): SquareMap {
    val sy = this.size
    val sx = this[0].length
    val map = mutableMapOf<Point, Char>()
    for (y in this.indices) {
        for (x in this[y].indices) {
            val p = Point(x, y)
            val c = this[y][x]
            map[p] = c
        }
    }
    return SquareMap(sx, sy, map)
}

data class SquareMap(val sizeX: Int, val sizeY: Int, val data: Map<Point, Char>) {
    operator fun get(x: Int, y: Int) = this[Point(x, y)]
    operator fun get(p: Point) = data[p]
    operator fun contains(p: Point) = p in data
    override fun toString() = (0 until sizeY).joinToString("\n") { y ->
        (0 until sizeX).joinToString("") { x -> "${data[Point(x, y)] ?: ' '}" }
    }

    val bottomRight = Point(sizeX - 1, sizeY - 1)
    val keys = data.keys
    fun cyclic() = SquareMapCyclic(sizeX, sizeY, data)
}

data class SquareMapCyclic(val sizeX: Int, val sizeY: Int, val data: Map<Point, Char>) {
    operator fun get(x: Int, y: Int) = this[Point(x, y)]
    operator fun get(p: Point) = data[Point((p.x % sizeX + sizeX) % sizeX, (p.y % sizeY + sizeY) % sizeY)]
    operator fun contains(p: Point) = true
    override fun toString() = (0 until sizeY).joinToString("\n") { y ->
        (0 until sizeX).joinToString("") { x -> "${data[Point(x, y)] ?: ' '}" }
    }

    val bottomRight = Point(sizeX - 1, sizeY - 1)
    val keys = data.keys
}

fun Sequence<Long>.tryPredictSequence(elem: Int, uprove: Int = 2): Long {
    val list = mutableListOf<Long>()

    val iterator = iterator()
    var uproved = 0
    while (true) {
        if (!iterator.hasNext()) error("not found")
        list += iterator.next()

        if (list.size > elem) {
            return list.last().toLong()
        }
        if (list.size >= 4) {
            val (x1, x2, x3, x4) = list.subList(list.size - 4, list.size)
            val x = x3
            val d = x3 - x2
            val dd = x3 - x2 - x2 + x1
            fun calc(elem: Int): Long {
                val st = (elem - list.size + 2).toLong()
                return x + d * st + dd * st * (st + 1) / 2
            }

            val next = calc(list.size - 1)
            println("${list.size - 4}\t$x\t$d\t$dd")
            if (x4 == next) {
                uproved++
                if (uprove <= uproved) {
                    return calc(elem)
                }
            } else {
                uproved = 0
            }
        }
    }
}

fun <T> Sequence<T>.pairs(): Sequence<Pair<T, T>> {
    val list = mutableListOf<T>()
    val iter = iterator()
    return sequence {
        for (cur in iter) {
            yieldAll(list.map { it to cur })
            list += cur
        }
    }
}

fun <T> Collection<T>.toCountMap(): Map<T, Int> {
    val result = mutableMapOf<T, Int>()
    forEach {
        result[it] = (result[it] ?: 0) + 1
    }
    return result
}

fun <T> List<T>.removeAt(idx: Int): List<T> {
    return subList(0, idx) + subList(idx + 1, size)
}

fun <T> Collection<T>.zipAllPairs(): Sequence<Pair<T, T>> = sequence {
    val l = toList()
    for (i in indices) {
        for (j in (i + 1) until size) {
            yield(l[i] to l[j])
        }
    }
}

fun <T> Collection<T>.repeat(num: Int): List<T> {
    val list = mutableListOf<T>()
    repeat(num) {
        list += this
    }
    return list
}

fun Collection<String>.toInt() = map{it.toInt()}

fun Iterable<BigInteger>.sum() = reduce { a, b -> a + b }