import kotlin.math.abs

fun main() {
    fun parse(input: List<String>): List<List<Int>> {
        return input.map { it -> it.split(" ").map { it.toInt() } }
    }

    fun isReportSafe(levels: List<Int>): Boolean {
        if (levels.count() <= 1) return true

        val differences = levels.zipWithNext().map { it.first - it.second }
        val allIncreasing = differences.all { it > 0 }
        val allDecreasing = differences.all { it < 0 }

        return (allIncreasing || allDecreasing) && differences.all { abs(it) in 1..3 }
    }

    fun getCombinations(list: List<Int>, k: Int): List<List<Int>> {
        if (k == 0) return listOf(emptyList()) // Base case: one empty combination when k is 0
        if (list.isEmpty()) return emptyList() // Base case: no combinations if the list is empty

        val first = list.first()
        val rest = list.drop(1)

        // Include the first element in the combination
        val includeFirst = getCombinations(rest, k - 1).map { it + first }

        // Exclude the first element from the combination
        val excludeFirst = getCombinations(rest, k)

        return includeFirst + excludeFirst
    }

    fun isReportPotentiallySafe(levels: List<Int>): Boolean {
        if (isReportSafe(levels)) return true

        return getCombinations(levels, levels.count() - 1).any { isReportSafe(it) }
    }

    fun part1(input: List<String>): Int {
        return parse(input).count { isReportSafe(it) }
    }

    fun part2(input: List<String>): Int {
        return parse(input).count { isReportPotentiallySafe(it) }
    }

    val testInput = readLines("Data/Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readLines("Data/Day02")
    part1(input).println()
    part2(input).println()
}
