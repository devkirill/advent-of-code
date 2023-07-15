package advent.calendar.aoc.utils

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object Utils {
    val cookie = System.getenv()["cookie"] ?: System.getProperty("cookie")

    fun getInput(year: Int, day: Int) =
        readUrl("https://adventofcode.com/${year}/day/${day}/input").removeSuffix("\n").split("\n")

    fun readUrl(urlString: String): String {
        val con: HttpURLConnection = URL(urlString).openConnection() as HttpURLConnection
        con.setRequestMethod("GET")
        con.setRequestProperty("Cookie", cookie)
        con.setDoOutput(true)
        if (con.responseCode != 200) {
            throw IllegalStateException("${con.responseCode} - ${con.responseMessage}")
        }
        val content = String((con.content as InputStream).readAllBytes())
        return content
    }

    fun send(year: Int, day: Int, level: Int, answer: Long) {
        val con: HttpURLConnection = URL("https://adventofcode.com/$year/day/$day/answer").openConnection() as HttpURLConnection
        con.setRequestMethod("POST")
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        con.setRequestProperty("Cookie", cookie)
        con.setDoOutput(true)
        con.outputStream.use { os ->
            val input = "level=$level&answer=$answer".toByteArray()
            os.write(input, 0, input.size)
        }
        if (con.responseCode != 200) {
            throw IllegalStateException("${con.responseCode} - ${con.responseMessage}")
        }
        val content = String((con.content as InputStream).readAllBytes())
        content
    }
}
