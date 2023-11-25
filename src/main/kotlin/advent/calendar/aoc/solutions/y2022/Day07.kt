package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component

@Component
class Day07 : Solution<Day07.Dir>() {
    override fun parse(lines: List<String>): Dir {
        val root = Dir()
        var cur = root

        for (line in lines.joinToString("\n").split("$ ").filter { it.isNotBlank() }.map { it.trim().split("\n") }) {
            val command = line[0].split(" ")
            val list = line.drop(1)
            when  {
                command[0].startsWith("cd") -> {
                    cur = if (command[1] == "/") {
                        root
                    } else if (command[1] == "..") {
                        cur.parent!!
                    } else {
                        cur.dirs[command[1]]!!
                    }
                }
                command[0].startsWith("ls") -> {
                    list.forEach {
                        val (a,b) = it.split(" ")
                        if (a == "dir") {
                            cur.dirs[b] = Dir(parent = cur)
                        } else {
                            cur.files[b] = a.toInt()
                        }
                    }
                }
            }
        }

        return root
    }

    override fun part1(input: Dir): Int {
        return input.dirs.values.sumOf { part1(it) } + if (input.size <= 100000) input.size else 0
    }

    override fun part2(input: Dir): Int {
        val need = input.size - 40000000
        fun search(dir: Dir): Int {
            return if (dir.size >= need) {
                kotlin.math.min(dir.size, dir.dirs.values.minOfOrNull { search(it) } ?: 70000000)
            } else {
                70000000
            }
        }
        return search(input)
    }

    data class Dir(val parent: Dir? = null, val dirs: MutableMap<String, Dir> = mutableMapOf(), val files: MutableMap<String, Int> = mutableMapOf()) {
        val size: Int get() = dirs.values.sumOf { it.size } + files.values.sum()

        override fun toString(): String {
            return "$size"
        }
    }
}
