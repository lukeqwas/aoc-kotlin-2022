package Day2

import readInput
import java.lang.IllegalArgumentException

val ROCK_POINTS = 1
val PAPER_POINTS = 2
val SCISSORS_POINTS = 3

fun main() {
    val testInput = readInput("Day2/Day02")
    var totalPoints = 0L
    val rockNode = Node("A", null, null)
    val paperNode = Node("B", rockNode, null)
    val scissorsNode = Node("C", paperNode, rockNode)
    paperNode.next = scissorsNode
    rockNode.next = paperNode
    rockNode.prev = scissorsNode
    for(input in testInput) {
        val opponentChoice = input.get(0).toString()
        val myChoice = input.get(2).toString()
        // find their node and then based on that do a tie win or loss depeneding on myChoice
        val theirNode = findNode(opponentChoice, rockNode)
        val myNode = findCorrespondingNode(theirNode, myChoice)

//        val myNode = findNode(myChoice, rockNode)
        totalPoints += calculcatePoints(myNode, opponentChoice)
    }
    println(totalPoints)
}

fun calculcatePoints(yourChoice: Node, opponentChoice: String): Int {
    var bonusPoints = 0
    if(yourChoice.prev?.value.equals(opponentChoice)){      // if you won
        bonusPoints = 6
    } else if(yourChoice.value == opponentChoice){
        bonusPoints = 3
    }
    return getChoicePoints(yourChoice.value) + bonusPoints

}

fun getChoicePoints(choice: String): Int {
    return when(choice) {
        "A" -> ROCK_POINTS
        "B" -> PAPER_POINTS
        "C" -> SCISSORS_POINTS
        else -> 0
    }
}

class Node(val value: String, var prev: Node?, var next: Node?){}

fun findNode(yourChoice: String, head: Node): Node {
    var headNode = head
    val normalizedChoice = (yourChoice)
    var timeout = 3
    while(timeout > 0) {
        if (normalizedChoice == headNode.value){
            return headNode
        }
        headNode = headNode.next!!
        timeout--
    }
    throw IllegalArgumentException("No node found for: ${yourChoice} -> ${normalizedChoice}")
}
fun findCorrespondingNode(theirNode: Node, myChoice: String): Node {
    return when(myChoice) {
        "X" -> theirNode.prev!!
        "Y" -> theirNode
        "Z" -> theirNode.next!!
        else -> throw IllegalArgumentException("No node found for theirNode:${theirNode.value} and myChoice: ${myChoice}")
    }
}
