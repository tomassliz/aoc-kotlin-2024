fun main() {
    val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

    fun processMultiplication(match: MatchResult): Int {
        val (num1, num2) = match.destructured

        return num1.toIntOrNull()?.let { n1 ->
            num2.toIntOrNull()?.let { n2 ->
                n1 * n2
            }
        } ?: 0
    }

    fun part1(input: String): Int {
        return mulRegex.findAll(input).sumOf { processMultiplication(it) }
    }

    fun part2(input: String): Int {
        val doRegex = Regex("""do\(\)""")
        val dontRegex = Regex("""don't\(\)""")

        var sum = 0
        var enabled = true

        var index = 0
        while (index < input.length) {
            val substring = input.substring(index)
            val mulMatch = mulRegex.find(substring)
            val doMatch = doRegex.find(substring)
            val dontMatch = dontRegex.find(substring)

            when {
                mulMatch != null && mulMatch.range.first == 0 -> {
                    if (enabled) sum += processMultiplication(mulMatch)

                    index += mulMatch.range.last + 1
                }

                doMatch != null && doMatch.range.first == 0 -> {
                    enabled = true
                    index += doMatch.range.last + 1
                }

                dontMatch != null && dontMatch.range.first == 0 -> {
                    enabled = false
                    index += dontMatch.range.last + 1
                }

                else -> {
                    index += 1
                }
            }
        }

        return sum
    }

    check(part1("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))") == 161)
    check(part2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)do()?mul(8,5))") == 48)

    val input = read("Data/Day03")
    part1(input).println()
    part2(input).println()
}
