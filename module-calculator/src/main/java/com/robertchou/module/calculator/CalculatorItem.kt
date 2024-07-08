package com.robertchou.module.calculator

data class CalculatorItem(
	val value: String = "",
	val isNegative: Boolean = false
) {
	fun isNumber(): Boolean {
		return value.toBigDecimalOrNull() != null || (value.isEmpty() && isNegative)
	}
}

