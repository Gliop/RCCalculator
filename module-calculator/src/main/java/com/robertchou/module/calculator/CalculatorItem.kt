package com.robertchou.module.calculator

/**
 * Data class representing a calculator item.
 *
 * @property value The string representation of the value.
 * @property isNegative Indicates if the value is negative.
 */
data class CalculatorItem(
	val value: String = "",
	val isNegative: Boolean = false
) {
	/**
	 * Checks if the value is a number.
	 *
	 * @return true if the value can be converted to BigDecimal or if the value is empty and isNegative is true.
	 */
	fun isNumber(): Boolean {
		return value.toBigDecimalOrNull() != null || (value.isEmpty() && isNegative)
	}
}
