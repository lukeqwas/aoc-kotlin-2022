package Day1

import readInput

fun main() {
    // 4*O(N) == O(N)
    val testInput = readInput("Day01")
    val totalElfCalories = mutableListOf<Long>()
    var curCalorie = 0L
    for (calorie in testInput) {
        if(calorie.equals("")) {
            totalElfCalories.add(curCalorie)
            curCalorie = 0
        } else {
            curCalorie += calorie.toLong()
        }
    }
    // dont forget the last one
    totalElfCalories.add(curCalorie)

    val result1 = findMax(totalElfCalories)
    val result2 = findMax(result1.list)
    val result3 = findMax(result2.list)
    println(result1.max + result2.max + result3.max)
}

fun findMax(elfCalories: MutableList<Long>): RemoveResult {
    var max = 0L
    var maxIndex = 0
    var curIndex = 0
    for (totalCalorie in elfCalories) {
        if(totalCalorie > max) {
            max = totalCalorie
            maxIndex = curIndex
        }
        curIndex++
    }
    elfCalories.removeAt(maxIndex)
    return RemoveResult(max, elfCalories)
}

data class RemoveResult(val max: Long, val list: MutableList<Long>)

