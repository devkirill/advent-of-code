package advent.calendar.aoc

import advent.calendar.aoc.exceptions.NotReleased
import advent.calendar.aoc.utils.ConsoleColors
import jakarta.annotation.PostConstruct
import java.util.*

class Testing(val solutions: List<Solution<*>>) {
    @PostConstruct
    fun init() {
        solutions
            .sortedByDescending { if (it.day == 25) 0 else it.day }
            .sortedByDescending { it.year }
            .forEach { solution -> solution.calculate() }
    }

    fun <T> Solution<T>.calculate() {
        val lines = AOC.input(year, day)
        val answers = AOC.getAnswers().filter { it.year == year && it.day == day && it.valid }
        var started = false
        if (answers.none { it.level == 1 }) {
            print("$year-${day / 10}${day % 10} ")
            test(lines, 1)
            started = true
        }
        if (answers.none { it.level == 2 } && day != 25) {
            if (!started) {
                print("$year-${day / 10}${day % 10} .")
            }
            test(lines, 2)
            started = true
        }
        if (started) {
            println()
        }
    }

    fun <T> Solution<T>.test(lines: List<String>, level: Int) {
        val begin = Date().time
        try {
            val input = parse(lines)
            val answer = (if (level == 1) part1(input) else part2(input)).toString()
            val result = AOC.send(year, day, level, answer)
            if (result) {
                print("${ConsoleColors.GREEN}✔${ConsoleColors.RESET}")
            } else {
                print("${ConsoleColors.RED}✖${ConsoleColors.RESET} ${answer} ")
            }
        } catch (_: NotReleased) {
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        val end = Date().time
        if (end - begin > 5000L) {
            print(" at ${end - begin}ms")
        }
    }
}
