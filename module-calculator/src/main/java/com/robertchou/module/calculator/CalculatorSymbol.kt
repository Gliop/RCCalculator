package com.robertchou.module.calculator

/**
 * Object that holds calculator symbols and operators.
 */
object CalculatorSymbol {
	const val AC = "AC"
	const val C = "C"
	const val PLUS_MINUS = "+/-"
	const val PERCENT = "%"
	const val DIVIDE = "÷"
	const val MULTIPLY = "×"
	const val SUBTRACT = "-"
	private const val ADD = "+"
	const val EQUALS = "="
	const val DEL = "DEL"
	const val DOT = "."

	/**
	 * Set of operator symbols.
	 */
	val operators = setOf(ADD, SUBTRACT, MULTIPLY, DIVIDE)
}
