package advent.calendar.aoc.logger

import advent.calendar.aoc.utils.ConsoleColors

object Logger {
    fun ok(msg: String) {
        println("${ConsoleColors.GREEN}✔${ConsoleColors.RESET} $msg")
    }

    fun info(msg: String) {

    }

    fun warn(msg: String, newLine: Boolean = false) {
        val line = "${ConsoleColors.YELLOW}?${ConsoleColors.RESET} $msg"
        if (newLine) println(line) else print(line)
    }

    fun error(msg: String) {
        println("${ConsoleColors.RED}✖${ConsoleColors.RESET} $msg")
    }
}
