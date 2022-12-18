package Day5

import readInput

//data class Range(val lowerBound: Int, val upperBound: Int)



fun main() {
    val testInput = readInput("Day4/Day04")
    var numPairs = 0

    for(input in testInput) {
        // find the smallest lower bound and see if the other range falls in the smalles lower bound range
        val ranges = input.split(',')
        val range1 = Range(ranges[0].split('-')[0].toInt(), ranges[0].split('-')[1].toInt())
        val range2 = Range(ranges[1].split('-')[0].toInt(), ranges[1].split('-')[1].toInt())
        if(range1.lowerBound >= range2.lowerBound && range1.lowerBound <= range2.upperBound) {
            numPairs++
        } else if(range2.lowerBound >= range1.lowerBound && range2.lowerBound <= range1.upperBound) {
            numPairs++
        }
    }
    println(numPairs)


}

class Range(private val num1: Int, private val num2: Int) {
    val lowerBound: Int
    val upperBound: Int
    init {
        lowerBound = Math.min(num1, num2)
        upperBound = Math.max(num1, num2)
    }
    fun isSmaller(otherRange: Range): Boolean {
        return Math.min(this.lowerBound, this.upperBound) < Math.min(otherRange.lowerBound, otherRange.upperBound)
    }
}
