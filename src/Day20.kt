fun main() {
    fun run(input: List<String>, times: Int): Int {
        val a = input.first().mapIndexed { i, c -> i to c }.filter { it.second == '#' }.map { it.first }
        val list = input.drop(2)

        var rangeX = list.indices.extend(2*times + 2)
        var rangeY = list[0].indices.extend(2*times + 2)

        var points = mutableSetOf<Point>()
        for (y in list.indices) {
            val s = list[y]
            for (x in s.indices) {
                if (s[x] == '#') {
                    points += Point(x, y)
                }
            }
        }

        repeat(times) {
            val l = mutableSetOf<Point>()
            rangeX = rangeX.extend(-1)
            rangeY = rangeY.extend(-1)

            for (x in rangeX) {
                for (y in rangeY) {
                    val p = Point(x, y)
                    val n = p.around().map { if (it in points) 1 else 0 }.fold(0) { a, b -> a * 2 + b }
                    if (n in a) {
                        l += p
                    }
                }
            }

            points = l
        }

        return points.size
    }

    runSolve(20, 35) { run(it, 2) }
    runSolve(20, 3351) { run(it, 50) }
}
