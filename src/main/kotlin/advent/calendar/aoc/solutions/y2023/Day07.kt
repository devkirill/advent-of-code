package advent.calendar.aoc.solutions.y2023

import advent.calendar.aoc.Solution

class Day07 : Solution<List<Day07.Hand>>() {
    override fun parse(lines: List<String>) = lines.map { line ->
        val (cards, bid) = line.split(" ")
        val pCards = cards.split("").filter { it.isNotBlank() }.map {
            when (it) {
                "T" -> 10
                "J" -> 11
                "Q" -> 12
                "K" -> 13
                "A" -> 14
                else -> it.toInt()
            }
        }
        Hand(pCards, bid.toInt())
    }

    override fun part1(input: List<Hand>) = input
        .onEach { hand ->
            val countCards = hand.cards.groupBy { it }.map { (k, v) -> k to v.size }.toMap()
            hand.rank = when {
                countCards.size == 1 -> 7
                countCards.any { (_, v) -> v == 4 } -> 6
                countCards.any { (_, v) -> v == 3 } && countCards.any { (_, v) -> v == 2 } -> 5
                countCards.any { (_, v) -> v == 3 } -> 4
                countCards.count { (_, v) -> v == 2 } == 2 -> 3
                countCards.any { (_, v) -> v == 2 } -> 2
                else -> 1
            }
        }.sorted().mapIndexed { i, hand -> hand.bid * (i + 1) }.sum()

    override fun part2(input: List<Hand>) = input
        .map { hand -> hand.copy(cards = hand.cards.map { if (it == 11) 1 else it }) }
        .onEach { hand ->
            val countCards = hand.cards.groupBy { it }.map { (k, v) -> k to v.size }.toMap().toMutableMap()
            val j = countCards[1] ?: 0
            countCards.remove(1)
            hand.rank = when {
                countCards.any { (_, v) -> v + j == 5 } || j == 5 -> 7
                countCards.any { (_, v) -> v + j == 4 } || j == 4 -> 6
                countCards.size == 2 && countCards.count { (_, v) -> v + j == 3 || v == 2 } == 2 -> 5
                countCards.size == 2 && countCards.count { (_, v) -> v == 3 || v + j == 2 } == 2 -> 5
                countCards.any { (_, v) -> v + j == 3 } || j == 3 -> 4
                countCards.count { (_, v) -> v == 2 } == 2 -> 3
                (2..14).any { a ->
                    (2..14).any { b ->
                        a != b && (countCards[a] ?: 0) + j == 2 && (countCards[b] ?: 0) == 2
                    }
                } -> 3

                countCards.any { (_, v) -> v + j == 2 } -> 2
                else -> 1
            }
        }
        .sorted()
        .mapIndexed { i, hand -> hand.bid * (i + 1) }
        .sum()

    data class Hand(val cards: List<Int>, val bid: Int) : Comparable<Hand> {
        var rank = 0

        override fun compareTo(other: Hand): Int {
            val r = this.rank.compareTo(other.rank)
            if (r != 0) {
                return r
            }
            for (i in 0..4) {
                val r = this.cards[i].compareTo(other.cards[i])
                if (r != 0) {
                    return r
                }
            }
            return 0
        }
    }
}

