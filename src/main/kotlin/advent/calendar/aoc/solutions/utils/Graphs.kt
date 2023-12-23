package advent.calendar.aoc.solutions.utils

fun <T> findComp(nodes: List<T>, nears: (T) -> List<T>) = sequence<Set<T>> {
    val used = mutableSetOf<T>()
    val comp = mutableListOf<T>()
    fun dfs(v: T) {
        used += v
        comp += v
        for (to in nears(v)) {
            if (to !in used) {
                dfs(to)
            }
        }
    }
    for (node in nodes) {
        if (node !in used) {
            comp.clear()
            dfs(node)
            yield(comp.toSet())
        }
    }
}.toList()
