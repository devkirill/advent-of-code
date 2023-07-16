package advent.calendar.aoc

import org.reflections.Reflections

//@SpringBootApplication
//class AocApplication

fun main() {
//	runApplication<AocApplication>(*args)

	val reflections = Reflections("${AOC::class.java.packageName}.solutions")
	val solutions = reflections.getSubTypesOf(Solution::class.java).map {
		val ctor = it.getConstructor()
		ctor.newInstance()
	}
	Testing(solutions).init()
}
