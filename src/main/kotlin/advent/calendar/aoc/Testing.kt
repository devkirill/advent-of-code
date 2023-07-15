package advent.calendar.aoc

import advent.calendar.aoc.utils.Utils
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Testing(
    @Autowired
    val solutions: List<Solution<*, *>>
) {
    @PostConstruct
    fun init() {
        solutions.forEach { solution ->
            solution.calculate()
        }

        Utils.send(2022, 2, 1, 123)
    }

    fun <A, B: Number> Solution<A, B>.calculate() {
        val lines = Utils.readUrl("https://adventofcode.com/${year}/day/${day}/input").removeSuffix("\n").split("\n")
        val input = parseInput(lines)
        val res1 = part1(input).toLong()
        val res2 = part2(input).toLong()
        println("debug")
    }
}
