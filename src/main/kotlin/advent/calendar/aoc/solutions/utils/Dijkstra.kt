package advent.calendar.aoc.solutions.utils

import java.util.*

fun <T> dijkstraSearch(
    startingPoints: List<T>,
    neighborProducer: (T) -> List<T>,
    distanceFunction: (T) -> Int,
): DijkstraResult<T> {
    data class State(val node: T, val distance: Int, val prev: State?)

    val bestDistance = DijkstraResult<T>()
    val boundary = PriorityQueue<State>(compareBy { it.distance })

    for (start in startingPoints) boundary += State(start, 0, null)

    while (boundary.isNotEmpty()) {
        val state = boundary.poll()
        val (currentNode, currentDistance) = state
        if (currentNode in bestDistance) continue

        bestDistance[currentNode] = currentDistance
        bestDistance.prevs[currentNode] = state.prev?.node

        for (nextNode in neighborProducer(currentNode)) {
            if (nextNode !in bestDistance) {
                boundary += State(nextNode, currentDistance + distanceFunction(nextNode), state)
            }
        }
    }

    return bestDistance
}

class DijkstraResult<T>() : LinkedHashMap<T, Int>() {
    val prevs = mutableMapOf<T, T?>()

    fun getPathTo(pos: T): List<T> {
        return sequence {
            var cur: T? = pos
            while (cur != null) {
                yield(cur)
                cur = prevs[cur]
            }
        }.toList()
    }
}
