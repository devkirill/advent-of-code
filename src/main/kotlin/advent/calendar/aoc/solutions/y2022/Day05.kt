package advent.calendar.aoc.solutions.y2022

import advent.calendar.aoc.Solution
import org.springframework.stereotype.Component
import java.util.LinkedList

@Component
class Day05 : Solution<Day05.Content>() {
    override fun parse(lines: List<String>): Content {
        val cargo = lines.takeWhile { it.isNotBlank() }.let { cargo->
            cargo.last().split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .associateWith { c ->
                    LinkedList(cargo.dropLast(1).map { it[(c - 1) * 4 + 1] }.filter { it != ' ' })
                }
        }
        val list = lines.filter{"move" in it}
            .map { it.split(" ") }
            .map { Action(count = it[1].toInt(), from = it[3].toInt(), to = it[5].toInt()) }
        return Content(cargo, list)
    }

    override fun part1(input: Content): String {
        val cargo = input.cargo
        input.actions.forEach { action ->
            val stack = LinkedList<Char>()
            repeat(action.count) {
                stack.push(cargo[action.from]!!.pop())
            }
            while (stack.isNotEmpty()) {
                cargo[action.to]!!.push(stack.pollLast())
            }
        }
        return cargo.keys.map {cargo[it]!!.first }.joinToString("") { it.toString() }
    }
    override fun part2(input: Content): String {
        val cargo = input.cargo
        input.actions.forEach { action ->
            val stack = LinkedList<Char>()
            repeat(action.count) {
                stack.push(cargo[action.from]!!.pop())
            }
            while (stack.isNotEmpty()) {
                cargo[action.to]!!.push(stack.poll())
            }
        }
        return cargo.keys.map {cargo[it]!!.first }.joinToString("") { it.toString() }
    }

    data class Content(val cargo: Map<Int, LinkedList<Char>>, val actions: List<Action>)
    data class Action(val from: Int, val to : Int, val count: Int)
}
