package Day4

import readInput
import java.util.*

//data class Range(val lowerBound: Int, val upperBound: Int)



fun main() {
    val testInput = readInput("Day5/Day05_test")

    var commandIndex = 0
    for(i in 1..testInput.size) {
        if ("move \\d+ from".toRegex().containsMatchIn(testInput[i-1])) {
            commandIndex = i-1
            break
        }
    }
    val stringNumCols = testInput[commandIndex-2]
    val numColumns = stringNumCols.split(' ').size
    val stacks = mutableListOf<Stack<String>>()

}

//fun buildStacks(input: String): List<Stack<String>> {
//
//}