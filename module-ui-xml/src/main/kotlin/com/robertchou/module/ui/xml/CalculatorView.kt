package com.robertchou.module.ui.xml

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.viewbinding.ViewBinding
import com.robertchou.module.ui.xml.databinding.CustomviewCalculatorBinding
import com.robertchou.module.ui.xml.databinding.CustomviewCalculatorLandBinding

class CalculatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: ViewBinding
    private var onButtonClickListener: (String) -> Unit = {}

    /**
     * Set the listener for calculator button clicks
     */
    fun setOnCalculatorButtonClickListener(listener: (String) -> Unit) {
        onButtonClickListener = listener
    }

    init {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        // Inflate the appropriate layout based on screen dimensions
        binding = if ((screenWidth * 5 / 4) > screenHeight * 0.7) {
            CustomviewCalculatorLandBinding.inflate(LayoutInflater.from(context), this, true)
        } else {
            CustomviewCalculatorBinding.inflate(LayoutInflater.from(context), this, true)
        }
        initialClickListener()
    }

    /**
     * Initialize click listeners for all buttons in the grid layout
     */
    private fun initialClickListener() {
        when (binding) {
            is CustomviewCalculatorBinding -> {
                val customviewCalculatorBinding = binding as CustomviewCalculatorBinding
                customviewCalculatorBinding.apply {
                    (binding.root.findViewById<GridLayout>(R.id.gl_bottom)).forEach {
                        run {
                            it.setOnClickListener {
                                onButtonClickListener((it as AutoResizeButton).text.toString())
                                scrollToRight()
                            }
                        }
                    }
                }
            }

            is CustomviewCalculatorLandBinding -> {
                val customviewCalculatorLandBinding = binding as CustomviewCalculatorLandBinding
                customviewCalculatorLandBinding.apply {
                    (binding.root.findViewById<GridLayout>(R.id.gl_bottom)).forEach {
                        run {
                            it.setOnClickListener {
                                onButtonClickListener((it as AutoResizeButton).text.toString())
                                scrollToRight()
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Set the preview text in the calculator view
     */
    fun setPreviewText(displayText: String) {
        when (binding) {
            is CustomviewCalculatorBinding -> {
                val customviewCalculatorBinding = binding as CustomviewCalculatorBinding
                customviewCalculatorBinding.apply {
                    tvCalculatorPreview.text = displayText
                }
            }

            is CustomviewCalculatorLandBinding -> {
                val customviewCalculatorLandBinding = binding as CustomviewCalculatorLandBinding
                customviewCalculatorLandBinding.apply {
                    tvCalculatorPreview.text = displayText
                }
            }
        }
    }

    /**
     * Set the current text in the calculator view
     */
    fun setCurrentText(displayText: String) {
        when (binding) {
            is CustomviewCalculatorBinding -> {
                val customviewCalculatorBinding = binding as CustomviewCalculatorBinding
                customviewCalculatorBinding.apply {
                    tvCalculatorCurrent.text = displayText
                }
            }

            is CustomviewCalculatorLandBinding -> {
                val customviewCalculatorLandBinding = binding as CustomviewCalculatorLandBinding
                customviewCalculatorLandBinding.apply {
                    tvCalculatorCurrent.text = displayText
                }
            }
        }
    }

    /**
     * Scroll the text views to the right
     */
    fun scrollToRight() {
        when (binding) {
            is CustomviewCalculatorBinding -> {
                val customviewCalculatorBinding = binding as CustomviewCalculatorBinding
                customviewCalculatorBinding.apply {
                    hlCalculatorCurrent.post {
                        hlCalculatorCurrent.smoothScrollTo(hlCalculatorPreview.getChildAt(0).width, 0)
                    }
                    hlCalculatorPreview.scrollTo(tvCalculatorCurrent.width, 0)
                    hlCalculatorPreview.post {
                        hlCalculatorPreview.smoothScrollTo(hlCalculatorPreview.getChildAt(0).width, 0)
                    }
                }
            }

            is CustomviewCalculatorLandBinding -> {
                val customviewCalculatorLandBinding = binding as CustomviewCalculatorLandBinding
                customviewCalculatorLandBinding.apply {
                    hlCalculatorCurrent.post {
                        hlCalculatorCurrent.smoothScrollTo(hlCalculatorPreview.getChildAt(0).width, 0)
                    }
                    hlCalculatorPreview.scrollTo(tvCalculatorCurrent.width, 0)
                    hlCalculatorPreview.post {
                        hlCalculatorPreview.smoothScrollTo(hlCalculatorPreview.getChildAt(0).width, 0)
                    }
                }
            }
        }
    }

    /**
     * Show or hide the delete button based on the input parameter
     */
    fun isShowDelete(isShow: Boolean) {
        when (binding) {
            is CustomviewCalculatorBinding -> {
                val customviewCalculatorBinding = binding as CustomviewCalculatorBinding
                customviewCalculatorBinding.apply {
                    if (isShow) {
                        glBottom.btn0Type1.visibility = VISIBLE
                        glBottom.btnDecimalType1.visibility = VISIBLE
                        glBottom.btnDel.visibility = INVISIBLE
                        glBottom.btn0Type2.visibility = INVISIBLE
                        glBottom.btnDecimalType2.visibility = INVISIBLE
                    } else {
                        glBottom.btn0Type1.visibility = INVISIBLE
                        glBottom.btnDecimalType1.visibility = INVISIBLE
                        glBottom.btnDel.visibility = VISIBLE
                        glBottom.btn0Type2.visibility = VISIBLE
                        glBottom.btnDecimalType2.visibility = VISIBLE
                    }
                }
            }

            is CustomviewCalculatorLandBinding -> {
                val customviewCalculatorBinding = binding as CustomviewCalculatorLandBinding
                customviewCalculatorBinding.apply {
                    if (isShow) {
                        glBottom.btn0Type1.visibility = VISIBLE
                        glBottom.btnDecimalType1.visibility = VISIBLE
                        glBottom.btnDel.visibility = INVISIBLE
                        glBottom.btn0Type2.visibility = INVISIBLE
                        glBottom.btnDecimalType2.visibility = INVISIBLE
                    } else {
                        glBottom.btn0Type1.visibility = INVISIBLE
                        glBottom.btnDecimalType1.visibility = INVISIBLE
                        glBottom.btnDel.visibility = VISIBLE
                        glBottom.btn0Type2.visibility = VISIBLE
                        glBottom.btnDecimalType2.visibility = VISIBLE
                    }
                }
            }
        }
    }
}
