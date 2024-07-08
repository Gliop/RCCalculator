package com.robertchou.module.ui.xml

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatButton


class AutoResizeButton @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = R.attr.customButtonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

	// Called when the size of the view changes
	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		super.onSizeChanged(w, h, oldh, oldw)
		adjustTextSize()
	}

	// Adjust the text size based on the button's height
	private fun adjustTextSize() {
		val buttonHeight = height - paddingTop - paddingBottom
		if (buttonHeight > 0) {
			// Set text size to half of the button height
			setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonHeight.toFloat() / 2)
		}
	}
}
