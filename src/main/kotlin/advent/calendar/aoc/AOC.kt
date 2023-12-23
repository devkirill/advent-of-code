package advent.calendar.aoc

import advent.calendar.aoc.exceptions.WrongAnswer
import advent.calendar.aoc.logger.Logger
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.jsoup.Jsoup
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

fun createDirIfNotExist(dir: File) {
    if (!dir.exists()) {
        dir.mkdirs()
    }
}

object AOC {
    val cookie = System.getenv()["cookie"] ?: System.getProperty("cookie") ?: TODO("COOKIES!!!")

    fun input(year: Int, day: Int): List<String> {
        val file = File("input/$year/$day.txt")
        if (file.exists()) {
            return file.readLines()
        }
        createDirIfNotExist(File("input/$year"))
        val content = readUrl("https://adventofcode.com/${year}/day/${day}/input").removeSuffix("\n").split("\n")
        file.writeText(content.joinToString("\n"))
        return content
    }

    fun getInputs(year: Int, day: Int, part: Int): Pair<List<List<String>>, List<String>> {
        val doc = Jsoup.connect("https://adventofcode.com/$year/day/$day")
            .cookie("cookie", cookie)
            .get()
        val inputs = doc.select("main > .day-desc pre code:not(:has(em))").map { it.text() }
        val answers = if (part == 1)
            doc.select("main > .day-desc:not(:has(#part2)) p > code > em").map { it.text() }
        else
            doc.select("main > .day-desc:has(#part2) p > code > em").map { it.text() }
        return inputs.map { it.split("\n") } to answers
    }

    fun send(year: Int, day: Int, level: Int, ans: String): Boolean {
        try {
            val answers = getAnswers().filter { it.year == year && it.day == day && it.level == level }
            answers.firstOrNull { it.answer == ans }?.let { return it.valid }

            val answer = try {
                sendAndValidate(year, day, level, ans)
                Answer(year, day, level, ans, true)
            } catch (e: WrongAnswer) {
                Answer(year, day, level, ans, false)
            }

            csvWriter().open(File("result.csv"), append = true) {
                writeRow(answer.toRow())
            }
            return answer.valid
        } catch (e: Throwable) {
            println(ans)
            throw e
        }
    }

    private fun sendAndValidate(year: Int, day: Int, level: Int, answer: String) {
        val con: HttpURLConnection =
            URL("https://adventofcode.com/$year/day/$day/answer").openConnection() as HttpURLConnection
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
        when {
            "That's the right answer" in content ||
                    "You don't seem to be solving the right level" in content -> {
//                Logger.ok("That's the right answer")
            }

            "Did you already complete it" in content -> {
//                Logger.warn("Did you already complete it")
                val problemText = readUrl("https://adventofcode.com/$year/day/$day")
                if ("<p>Your puzzle answer was <code>$answer</code>.</p>" !in problemText) {
                    throw WrongAnswer()
                }
            }

            "That's not the right answer" in content -> {
//                Logger.error("That's not the right answer")
                throw WrongAnswer()
            }

            "You gave an answer too recently" in content -> {
                val res = Regex("You have (?:(\\d+)m )?(\\d+)s left to wait").find(content)!!
                fun String.parseInt() = if (isBlank()) 0 else toInt()
                var wait = res.groupValues[1].parseInt() * 60 + res.groupValues[2].parseInt()
                var msg = ""
                Logger.warn("", newLine = false)
                while (wait > 0) {
                    val m = wait / 60
                    val s = wait % 60
                    msg =
                        "You have ${if (m > 0) "${m}m " else ""}${if (s > 0) "${s}s " else ""}left to wait (answer = $answer)"
                    print(msg)
                    Thread.sleep(1000L)
                    wait--
                    print("\b".repeat(msg.length) + " ".repeat(msg.length) + "\b".repeat(msg.length))
                }
                print("\b\b")
                sendAndValidate(year, day, level, answer)
            }
        }
    }

    private fun readUrl(urlString: String): String {
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

    fun getAnswers(): List<Answer> {
        val file = File("result.csv")
        return if (file.exists())
            csvReader().readAll(File("result.csv")).map { Answer(it) }
        else
            listOf()
    }
}

data class Answer(val year: Int, val day: Int, val level: Int, val answer: String, val valid: Boolean) {
    companion object {
        operator fun invoke(columns: List<String>) =
            Answer(columns[0].toInt(), columns[1].toInt(), columns[2].toInt(), columns[3], columns[4].toBoolean())
    }

    fun toRow() = listOf(year, day, level, answer, valid).map { it.toString() }
}
