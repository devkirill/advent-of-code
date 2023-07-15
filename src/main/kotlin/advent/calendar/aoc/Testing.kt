package advent.calendar.aoc

import advent.calendar.aoc.exceptions.NotReleased
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

//        Utils.send(2022, 2, 1, 123)
//        AOC.send(2022, 1, 1, "123")
//        AOC.send(2022, 1, 1, "456")
//        AOC.send(2022, 1, 1, "69310")
    }

    fun <A, B : Number> Solution<A, B>.calculate() {
        val lines = AOC.input(year, day)
        try {
            val input = parse(lines)
            val res1 = part1(input).toString()
            AOC.send(year, day, 1, res1)
        } catch (_: NotReleased) {
        }
        try {
            val input = parse2(lines)
            val res2 = part2(input).toString()
            AOC.send(year, day, 2, res2)
        } catch (_: NotReleased) {
        }
    }
}
