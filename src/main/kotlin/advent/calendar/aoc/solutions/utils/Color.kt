package advent.calendar.aoc.solutions.utils

val ANSI_RESET = "\u001B[0m";
val ANSI_BLACK = "\u001B[30m";
val ANSI_RED = "\u001B[31m";
val ANSI_GREEN = "\u001B[32m";
val ANSI_YELLOW = "\u001B[33m";
val ANSI_BLUE = "\u001B[34m";
val ANSI_PURPLE = "\u001B[35m";
val ANSI_CYAN = "\u001B[36m";
val ANSI_WHITE = "\u001B[37m";

fun printColor(text: String, color: String) = println("$color$text$ANSI_RESET")

fun red(text: String) = "$ANSI_RED$text$ANSI_RESET"
fun green(text: String) = "$ANSI_GREEN$text$ANSI_RESET"
fun yellow(text: String) = "$ANSI_YELLOW$text$ANSI_RESET"
fun blue(text: String) = "$ANSI_BLUE$text$ANSI_RESET"
fun purple(text: String) = "$ANSI_PURPLE$text$ANSI_RESET"
fun cyan(text: String) = "$ANSI_CYAN$text$ANSI_RESET"