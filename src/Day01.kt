import kotlin.math.abs

fun main() {
    fun parse(input: List<String>): Pair<List<Int>, List<Int>> {
        val ints = input.map { it -> it.split("\\s+".toRegex()).map { it.toInt() } }
        return Pair(ints.map { it.first() }, ints.map { it.last() })
    }

    fun part1(input: List<String>): Int {
        val (lefts, rights) = parse(input)
        val sortedLefts = lefts.sorted()
        val sortedRights = rights.sorted()

        val totalDistance = sortedLefts.zip(sortedRights).fold(0) { acc, pair -> acc + abs(pair.first - pair.second) }

        return totalDistance
    }

    fun part2(input: List<String>): Int {
        val (lefts, rights) = parse(input)

        val rightCounts: Map<Int, Int> = rights.fold(mutableMapOf()) { acc, num ->
            acc[num] = acc.getOrDefault(num, 0) + 1
            acc
        }

        val similarityScore = lefts.fold(0) { score, num -> score + (num * rightCounts.getOrDefault(num, 0)) }

        return similarityScore
    }

    val testInput = readLines("Data/Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readLines("Data/Day01")
    part1(input).println()
    part2(input).println()
}
