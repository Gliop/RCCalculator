package com.robertchou.module.calculator

import com.ezylang.evalex.Expression
import java.math.BigDecimal
import java.util.EmptyStackException
import java.util.Stack

class CalculatorModel {

	private val stackCalculate = Stack<CalculatorItem>()

	// Get the last display item as a string
	fun getLastDisplay(): String {
		return try {
			convertToSignalString(stackCalculate.peek())
		} catch (_: EmptyStackException) {
			""
		}
	}

	// Check if shift operation can be performed
	fun isCanShift(): Boolean {
		return isCalculateFinished() || (stackCalculate.size == 1 && stackCalculate.peek().value.isNumber())
	}

	// Check if input can be received
	fun isCanReceive(): Boolean {
		return when {
			stackCalculate.isEmpty() -> true
			stackCalculate.peek().value in CalculatorSymbol.operators -> true
			isCalculateFinished() -> true
			else -> false
		}
	}

	// Convert the calculator item to a signal string
	private fun convertToSignalString(calculatorItem: CalculatorItem): String {
		return if (calculatorItem.isNegative) {
			"-${calculatorItem.value}"
		} else {
			calculatorItem.value
		}
	}

	// Get the last calculator item
	fun getLastCalculateItem(): CalculatorItem? {
		return try {
			stackCalculate.peek()
		} catch (_: EmptyStackException) {
			null
		}
	}

	// Get the current calculation process as a string
	fun getCalculateProcess(): String {
		val stringBuffer = StringBuffer()
		stackCalculate.forEach {
			if (it.isNegative) {
				stringBuffer.append("-")
			}
			stringBuffer.append(it.value)
		}
		return stringBuffer.toString().replace("+-", "-").replace("--", "")
	}

	// Handle the input text for calculation
	fun inputText(input: String): String {
		println("[$input] Before ${getCalculateProcess()}")
		println("[${CalculatorSymbol.PLUS_MINUS}] Before ${getCalculateProcess()}")
		when (input) {
			CalculatorSymbol.AC -> stackCalculate.clear()
			CalculatorSymbol.DEL -> {
				if (stackCalculate.isNotEmpty()) {
					try {
						val item = stackCalculate.pop()
						if (item.value.isNumber() || (item.value.isEmpty() && item.isNegative)) {
							val currentValue = if (item.isNegative) {
								CalculatorSymbol.SUBTRACT + item.value
							} else {
								item.value
							}
							if (currentValue.length > 1) {
								val lastValue = currentValue.substring(0, currentValue.length - 1)
								if (lastValue == CalculatorSymbol.SUBTRACT) {
									stackCalculate.push(CalculatorItem("", true))
								} else {
									stackCalculate.push(CalculatorItem(lastValue, false))
								}
							}
						}
					} catch (_: EmptyStackException) {
					}
				}
			}
			CalculatorSymbol.C -> { /* No operation for C */ }
			CalculatorSymbol.PLUS_MINUS -> {
				try {
					val item = stackCalculate.pop()
					if (isCalculateFinished()) {
						stackCalculate.clear()
						stackCalculate.push(CalculatorItem(item.value, !item.isNegative))
					} else if (item.value in CalculatorSymbol.operators) {
						stackCalculate.push(item)
						stackCalculate.push(CalculatorItem("", true))
					} else if (item.isNumber()) {
						stackCalculate.push(CalculatorItem(item.value, !item.isNegative))
					}
				} catch (_: EmptyStackException) {
					stackCalculate.push(CalculatorItem("", true))
				}
			}
			CalculatorSymbol.PERCENT -> {
				if (isCalculateFinished()) {
					val item = stackCalculate.pop()
					stackCalculate.clear()
					calculateDecimalFromPercentage(item.value).also {
						stackCalculate.push(item.copy(value = it))
					}
				} else {
					try {
						val item = stackCalculate.pop()
						if (item.isNumber()) {
							calculateDecimalFromPercentage(item.value).also {
								stackCalculate.push(item.copy(value = it))
							}
						} else if (item.value in CalculatorSymbol.operators) {
							stackCalculate.push(item)
							stackCalculate.push(CalculatorItem("0.01", false))
						}
					} catch (_: EmptyStackException) {
						stackCalculate.push(CalculatorItem("0.01", false))
					}
				}
			}
			in "0".."9" -> {
				try {
					if (isCalculateFinished()) {
						stackCalculate.clear()
						stackCalculate.push(CalculatorItem(input))
					} else {
						val item = stackCalculate.pop()
						if (item.isNumber()) {
							stackCalculate.push(item.copy(value = item.value + input))
						} else if (item.value in CalculatorSymbol.operators) {
							stackCalculate.push(item)
							stackCalculate.push(CalculatorItem(input))
						}
					}
				} catch (_: EmptyStackException) {
					stackCalculate.push(CalculatorItem(input))
				}
			}
			CalculatorSymbol.DOT -> {
				try {
					if (isCalculateFinished()) {
						val calculateResult = stackCalculate.pop()
						if (calculateResult.value.isInteger()) {
							stackCalculate.clear()
							stackCalculate.push(calculateResult.copy(value = calculateResult.value + input))
						}
					} else {
						val item = stackCalculate.peek()
						when {
							item.value.isInteger() -> {
								stackCalculate.pop()
								stackCalculate.push(item.copy(value = item.value + input))
							}
							item.value in CalculatorSymbol.operators -> {
								stackCalculate.pop()
								stackCalculate.push(item)
								stackCalculate.push(CalculatorItem("0${CalculatorSymbol.DOT}"))
							}
						}
					}
				} catch (_: EmptyStackException) {
					stackCalculate.push(CalculatorItem("0${CalculatorSymbol.DOT}"))
				}
			}
			in CalculatorSymbol.operators -> {
				try {
					if (isCalculateFinished()) {
						val calculateResult = stackCalculate.pop()
						stackCalculate.clear()
						stackCalculate.push(calculateResult)
						stackCalculate.push(CalculatorItem(input))
					} else {
						val item = stackCalculate.pop()
						when {
							item.isNumber() -> {
								stackCalculate.push(item)
								stackCalculate.push(CalculatorItem(input))
							}
							item.value in CalculatorSymbol.operators -> {
								stackCalculate.push(CalculatorItem(input))
							}
						}
					}
				} catch (_: EmptyStackException) {
				}
			}
			CalculatorSymbol.EQUALS -> {
				if (!getCalculateProcess().contains(CalculatorSymbol.EQUALS) && stackCalculate.size > 2) {
					try {
						val result = getCalculateResult()
						stackCalculate.push(CalculatorItem(CalculatorSymbol.EQUALS))
						stackCalculate.push(CalculatorItem(result.toPlainString(), result.signum() < 0))
					} catch (e: Exception) {
						println("CalculatorSymbol.EQUALS\t$e")
					}
				}
			}
		}

		return buildString {
			val stringBuffer = StringBuffer()
			stackCalculate.forEach {
				stringBuffer.append(it)
			}
			append(stringBuffer.toString())
		}
	}

	// Check if the calculation is finished
	private fun isCalculateFinished(): Boolean {
		return getCalculateProcess().contains(CalculatorSymbol.EQUALS)
	}

	// Insert a shift number into the calculation process
	fun insertShiftNumber(input: CalculatorItem): String {
		if (!input.isNumber()) {
			return getCalculateProcess()
		}
		try {
			val item = stackCalculate.peek()
			when {
				item.value in CalculatorSymbol.operators || getCalculateProcess().isEmpty() -> {
					stackCalculate.push(input)
				}
				isCalculateFinished() -> {
					stackCalculate.clear()
					stackCalculate.push(input)
				}
				stackCalculate.size == 1 && stackCalculate.peek().value.isNumber() -> {
					stackCalculate.clear()
					stackCalculate.push(input)
				}
			}
		} catch (_: EmptyStackException) {
			stackCalculate.push(input)
		}
		return getCalculateProcess()
	}

	// Extension function: Check if a string is an integer
	private fun String.isInteger(): Boolean {
		return this.matches(Regex("^-?\\d+$"))
	}

	// Extension function: Check if a string is an integer with a dot
	private fun String.isIntegerWithDot(): Boolean {
		return this.matches(Regex("^-?\\d+\\.?$"))
	}

	// Extension function: Check if a string is a decimal
	private fun String.isDecimal(): Boolean {
		return this.matches(Regex("^-?\\d*\\.\\d+$"))
	}

	// Extension function: Check if a string is a number (including integer, decimal, and scientific notation)
	private fun String.isNumber(): Boolean {
		return this.isInteger() || this.isDecimal() || this.isIntegerWithDot()
	}

	// Calculate the decimal value from a percentage string
	private fun calculateDecimalFromPercentage(input: String): String {
		return try {
			val expression = Expression("$input/100")
			val result = expression.evaluate().numberValue
			result.toString() // Return the string representation of the decimal value
		} catch (e: NumberFormatException) {
			"Invalid input"
		}
	}

	// Get the result of the calculation
	private fun getCalculateResult(): BigDecimal {
		return try {
			val expression = Expression(getCalculateProcess().replace(CalculatorSymbol.MULTIPLY, "*").replace(CalculatorSymbol.DIVIDE, "/"))
			expression.evaluate().numberValue
		} catch (e: Exception) {
			BigDecimal.ZERO
		}
	}
}
