package com.robertchou.module.ui.xml

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView


class AutoResizeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.customButtonStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    // Called when the size of the view changes
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldh, oldw)
        adjustTextSize()
    }

    // Adjust the text size based on the view's height
    private fun adjustTextSize() {
        val viewHeight = height - paddingTop - paddingBottom
        if (viewHeight > 0) {
            // Set text size to 80% of the view height
            setTextSize(TypedValue.COMPLEX_UNIT_PX, viewHeight.toFloat() * 0.8f)
        }
    }
}
