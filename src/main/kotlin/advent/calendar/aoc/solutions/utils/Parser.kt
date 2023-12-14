package advent.calendar.aoc.solutions.utils

object Parser {
    fun parse(list: List<String>): String {
        val count = (list[0].length + 4) / 5
//        val maps = (0 until count).map { x ->
//            x to parseChar(list.map { line -> line.substring(x * 5, x * 5 + 4) })
//        }
//        if (maps.any { it. })
        return (0 until count).map { x ->
            parseChar(list.map { line -> line.substring(x * 5, x * 5 + 4) })
        }.joinToString("")
    }

    fun parseChar(list: List<String>): Char {
        val char = list.map { it.take(4) }.joinToString("").map { if (it == ' ') ' ' else '*' }.joinToString("")
        return when (char) {
            " ** *  **  ******  **  *" -> 'A'
            " ** *  **   *   *  * ** " -> 'C'
            "*****   *** *   *   ****" -> 'E'
            "*****   *** *   *   *   " -> 'F'
            " ** *  **   * ***  * ***" -> 'G'
            "*  **  ******  **  **  *" -> 'H'
            "  **   *   *   **  * ** " -> 'J'
            "*  ** * **  * * * * *  *" -> 'K'
            "*** *  **  **** *   *   " -> 'P'
            "*** *  **  **** * * *  *" -> 'R'
            "****   *  *  *  *   ****" -> 'Z'
            /***/
            else -> '?'
        }
    }
}
