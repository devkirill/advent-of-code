package advent.calendar.aoc

import advent.calendar.aoc.exceptions.NotReleased
import advent.calendar.aoc.utils.ConsoleColors
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
        solutions.sortedBy { it.day }.sortedByDescending { it.year }.forEach { solution ->
            solution.calculate()
        }
    }

    fun <A, B : Number> Solution<A, B>.calculate() {
        val lines = AOC.input(year, day)
        val answers = AOC.getAnswers()
        print("$year/${day / 10}${day % 10} ")
        test(lines, answers, 1)
        test(lines, answers, 2)
        println()
    }

    fun <T, R : Number> Solution<T, R>.test(lines: List<String>, answers: List<Answer>, level: Int) {
        try {
            if (answers.any { it.year == year && it.day == day && it.valid }) {
                return
            }
            val input = parse(lines)
            val answer = (if (level == 1) part1(input) else part2(input)).toString()
            val result = AOC.send(year, day, level, answer)
            if (result) {
                print("${ConsoleColors.GREEN}✔${ConsoleColors.RESET}")
            } else {
                print("${ConsoleColors.RED}✖${ConsoleColors.RESET}")
            }
        } catch (_: NotReleased) {
        }
    }
}
