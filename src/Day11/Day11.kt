package Day11

import readInput
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.math.BigInteger


fun main() {
    val testInput = readInput("Day11/Day11")
    val monkeyList = mutableListOf<Monkey>()
    var startingItems = mutableListOf<Item>()
    var operation = { x:BigDecimal -> BigDecimal.ZERO}
    var testAction = { x:BigDecimal -> false}
    var monkeyThrowTrue = 0
    var divisor  = BigDecimal.ONE

    for(input in testInput) {
        // create a monkey here
        if(input.contains("Starting items")) {
            // List of numbers
            val startingItemsString = input.split(":")[1].trim().split(",")
            startingItems = startingItemsString.map { Item(it.trim().toBigDecimal()) }.toMutableList()
        } else if( input.contains("Operation:")) {
            val x = input.split("= old ")[1].trim()
            operation = parseFunction(x)
        } else if(input.contains("Test: ")) {
            divisor = parseDivisor(input)
            testAction = parseTestAction(input, divisor)

        } else if(input.contains("If true: throw to monkey")) {
            monkeyThrowTrue = parseMonkeyThrow(input)
        } else if(input.contains("If false: throw to monkey")) {
            val monkeyThrowFalse = parseMonkeyThrow(input)
            monkeyList.add(Monkey(startingItems, operation, testAction, monkeyThrowTrue, monkeyThrowFalse, divisor))
        }
    }
    val modulo = monkeyList
        .map {it.divisor}
        .distinct()
        .fold(BigDecimal.ONE) { acc, div -> acc * div }

    calculateMonkeyBusiness(10_000, monkeyList, modulo)
    // find the top two monkeys
    monkeyList.sortByDescending { it.monkeyBusiness }


    for(monkey in monkeyList){
        println(monkey.monkeyBusiness)
    }
    println(BigInteger.valueOf(monkeyList[0].monkeyBusiness).multiply(BigInteger.valueOf(monkeyList[1].monkeyBusiness)))
}

fun parseMonkeyThrow(input: String): Int {
    return "\\d+".toRegex().find(input, 0)?.value?.toInt()!!
}

fun calculateMonkeyBusiness(rounds: Int, monkeys: MutableList<Monkey>, modulo: BigDecimal) {
    for(i in 0 until rounds) {
        for (monkey in monkeys) {
            for (item in monkey.items) {
                monkey.inspect()
                val worryLevel = monkey.operation(item.worryLevel) % modulo
//                println(worryLevel)
                val isTest = monkey.testAction(worryLevel)
                if (isTest) {
                    monkeys[monkey.monkeyThrowTrue].items.add(Item(worryLevel))
                } else {
                    monkeys[monkey.monkeyThrowFalse].items.add(Item(worryLevel))
                }
            }
            monkey.clearItems()
        }

    }
}

data class Monkey(var items: MutableList<Item>,
                  val operation:(scalar: BigDecimal)-> BigDecimal,
                  val testAction:(value: BigDecimal) -> Boolean,
                  val monkeyThrowTrue: Int,
                  val monkeyThrowFalse: Int, val divisor: BigDecimal) {
    var monkeyBusiness: Long = 0
    fun inspect() {
        this.monkeyBusiness++
    }

    fun clearItems() {
        items = mutableListOf()
    }


}

data class Item(val worryLevel: BigDecimal)

// input looks like <math_symbol> <number>
fun parseFunction(input: String): (x: BigDecimal) -> BigDecimal {
    // get the number first
    val scalar = "\\d+".toRegex().find(input, 0)?.value?.toBigDecimal() ?: return {y: BigDecimal -> y.pow(2)}
    when(input[0]) {
        '*' -> return {y: BigDecimal -> y.multiply(scalar) }
        '+' -> return {y: BigDecimal -> y.plus(scalar)}
        else -> throw IllegalArgumentException(input)
    }
}

fun parseTestAction(input: String, divisor: BigDecimal): (x: BigDecimal) -> Boolean {
//    val divisor = "\\d+".toRegex().find(input, 0)?.value?.toBigDecimal()
//        ?: throw IllegalArgumentException("$input has no divisor")
    return {x: BigDecimal -> (x.remainder(divisor)).equals(BigDecimal.ZERO)}
}

fun parseDivisor (input: String): BigDecimal {
    return  "\\d+".toRegex().find(input, 0)?.value?.toBigDecimal()
        ?: throw IllegalArgumentException("$input has no divisor")
}