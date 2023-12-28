package advent.calendar.aoc.solutions.utils.geom

import Jama.Matrix

operator fun Matrix.div(b: Matrix) = b.solve(this)!!

