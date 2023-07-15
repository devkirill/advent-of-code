package advent.calendar.aoc

import org.reflections.Reflections
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AocApplication

fun main() {
//	runApplication<AocApplication>(*args)

	val reflections = Reflections("advent.calendar.aoc.solutions")
	val solutions = reflections.getSubTypesOf(Solution::class.java).map {
		val ctor = it.getConstructor()
		ctor.newInstance()
	}
	Testing(solutions).init()
}
