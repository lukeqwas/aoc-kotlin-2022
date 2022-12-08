package Day3

import readInput
import java.lang.IllegalArgumentException

// http://brianaspinall.com/wp-content/uploads/2015/11/better_ascii_table.jpg

fun main() {
    val testInput = readInput("Day3/Day03")
    var prioritySum = 0
    var index = 1;
    val curList = mutableListOf<String>()
    for(input in testInput) {
        // group them into 3
        curList.add(input)
        if(index % 3 == 0) {
            val sharedItem = findSharedItem(curList)
            prioritySum += getPriority(sharedItem)
            curList.clear()
        }
        index++
    }
    println(prioritySum)
}

fun findSharedItem(items: List<String>): Char {
    val itemSet1 = mutableSetOf<Char>()
    itemSet1.addAll(items[0].map { it })
    val itemSet2 = mutableSetOf<Char>()
    itemSet2.addAll(items[1].map { it })
    val itemSet3 = mutableSetOf<Char>()
    itemSet3.addAll(items[2].map { it })
    for(item1 in itemSet1) {
        if(itemSet2.contains(item1) && itemSet3.contains(item1)) {
            return item1
        }
    }
    throw IllegalArgumentException("No shared elements between first:  and second ")
}

fun getPriority(item: Char): Int {
    if(item.isUpperCase()) {
        return (item.toInt()) - 38
    } else {
        return (item.toInt()) - 96
    }
}
