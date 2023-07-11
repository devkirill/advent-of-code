package loader

class Task(val day: Int, val year: Int = 2022) {
    companion object {
        operator fun invoke(day: Int, year: Int = 2022, aaa: () -> Unit) {

        }
    }
}