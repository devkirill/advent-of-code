package advent.calendar.aoc.solutions.utils

fun String.parse(regex: Regex) = regex.find(this)!!.groupValues

fun String.parse(regex: String) = parse(Regex(regex))

fun <T> String.parse(regex: Regex, map: (List<String>) -> T): T = map(parse(regex).drop(1))