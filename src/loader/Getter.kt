package loader

import java.io.*
import java.net.HttpURLConnection
import java.net.URL


object Getter {
    val currentYear = 2022

    fun get(filename: String, a: () -> List<String>): List<String> {
        val file = File(filename)
        if (!file.exists()) {
            val result = a().joinToString("\n")
            val writer = BufferedWriter(FileWriter(filename))
            writer.write(result)
            writer.close()
        }
        try {
            return file.readLines()
        } catch (e: Throwable) {
            println(file.absolutePath)
            throw e
        }
    }

    fun getFrom(url: String): List<String> {
        val con = URL(url).openConnection() as HttpURLConnection
        con.setRequestProperty("Cookie", System.getenv("cookie") ?: "")
        val `in` = BufferedReader(InputStreamReader(con.inputStream))
        var inputLine: String?
        val lines = mutableListOf<String>()
        while (`in`.readLine().also { inputLine = it } != null) lines += inputLine ?: ""
        `in`.close()
        return lines
    }

    fun getInput(day: Int, year: Int = 2022) = get("src/resources/$year-${"%02d".format(day)}.txt") {
        getFrom("https://adventofcode.com/$year/day/$day/input")
    }

    fun getText(day: Int, year: Int = 2022) = getFrom("https://adventofcode.com/$year/day/$day")

//    fun getTest(day: Int, year: Int = 2022) = get("src/resources/$year-$day.txt") {
//        getFrom("https://adventofcode.com/$year/day/$day/input")
//    }
}