package advent.calendar.aoc

import advent.calendar.aoc.exceptions.NotReleased
import advent.calendar.aoc.utils.ConsoleColors
import java.util.*

class Testing(val solutions: List<Solution<*>>) {
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
            testExamples(year, day, 1)
            test(lines, 1)
            started = true
        }
        if (answers.none { it.level == 2 } && day != 25) {
            if (!started) {
                print("$year-${day / 10}${day % 10} .")
            }
            testExamples(year, day, 2)
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
            val input = (if (level == 1) parse(lines) else parse2(lines))
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
            print(" at ${end - begin}ms ")
        }
    }

    fun <T> Solution<T>.testExamples(year: Int, day: Int, part: Int) {
        try {
            val (inputs, outputs) = AOC.getInputs(year, day, part)

            inputs.forEach { input ->
                val answer = solve(input, part)
                if (answer !in outputs) {
                    println("${input.joinToString("\n")}")
                    println("${ConsoleColors.RED}$answer${ConsoleColors.RESET}")
                    TODO()
                }
            }
        } catch (_: NotReleased) {
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun <T> Solution<T>.solve(lines: List<String>, part: Int): String {
        val input: T
        try {
            input = (if (part == 1) parse(lines) else parse2(lines))
        } catch (e: Throwable) {
            throw NotReleased()
        }
        return (if (part == 1) part1(input) else part2(input)).toString()
    }
}
