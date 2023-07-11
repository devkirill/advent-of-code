fun main() {
    fun String.small() = this[0] in 'a'..'z'

    fun part1(input: List<String>): Int {
        val graph = mutableMapOf<String, List<String>>()
        input.forEach {
            val a = it.split("-")
            graph[a[0]] = (graph[a[0]] ?: listOf()) + a[1]
            graph[a[1]] = (graph[a[1]] ?: listOf()) + a[0]
        }
        fun find(visited: List<String> = listOf(), last: String = ""): Int {
            if (last == "end") {
                return 1
            }
            var result = 0
            for (n in graph[last]!!) {
                if (n.small() && n !in visited || !n.small()) {
                    result += find(visited + n, n)
                }
            }
            return result
        }
        return find(listOf("start"), "start")
    }

    fun part2(input: List<String>): Int {
        val graph = mutableMapOf<String, List<String>>()
        input.forEach {
            val a = it.split("-")
            graph[a[0]] = (graph[a[0]] ?: listOf()) + a[1]
            graph[a[1]] = (graph[a[1]] ?: listOf()) + a[0]
        }
        fun find(visited: List<String> = listOf(), last: String = ""): Int {
            if (last == "end") {
                debug(visited)
                return 1
            }
            var result = 0
            for (n in graph[last]!!) {
                if (!n.small()) {
                    result += find(visited + n, n)
                } else if (n != "start") {
                    val v = visited.filter { it.small() }
                    val hasDouble = v.size == v.distinct().size
                    if (n !in visited || hasDouble) {
                        result += find(visited + n, n)
                    }
                }
            }
            return result
        }
        return find(listOf("start"), "start")
    }

    val testInput = readInput("Day12_test")
    assertEquals(part1(testInput), 10)
    assertEquals(part2(testInput), 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
