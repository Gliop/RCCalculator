package com.robertchou.rccalculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.robertchou.rccalculator.databinding.ActivityMainBinding
import androidx.activity.viewModels


class MainActivity :AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private val viewModelPortrait: MainActivityPortraitViewModel by viewModels()
	private val viewModelLandscape: MainActivityLandViewModel by viewModels()


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		initialPortraitScreen()
		observePortraitScreen()

		initialLandscapeScreen()
		observeLandscapeScreen()
	}

	private fun initialPortraitScreen() {
		binding.calculatorPortrait?.apply {
			setOnCalculatorButtonClickListener { input ->
				viewModelPortrait.clickPortraitButton(input)
			}
		}
	}

	private fun observePortraitScreen() {
		viewModelPortrait.calculatorDisplayPortrait.observe(this) {
			binding.calculatorPortrait?.setCurrentText(it)
		}
		viewModelPortrait.calculatorPreviewPortrait.observe(this) {
			binding.calculatorPortrait?.setPreviewText(it)
		}
	}


	private fun initialLandscapeScreen() {
		binding.cvLeft?.apply {
			setOnCalculatorButtonClickListener { input ->
				viewModelLandscape.clickLeftButton(input)
			}
		}

		binding.btnShiftLeft?.setOnClickListener {
			viewModelLandscape.shiftRightToLeft()
		}

		binding.btnShiftRight?.setOnClickListener {
			viewModelLandscape.shiftLeftToRight()
		}

		binding.cvRight?.apply {
			setOnCalculatorButtonClickListener { input ->
				viewModelLandscape.clickRightButton(input)
			}
		}
	}

	private fun observeLandscapeScreen() {
		viewModelLandscape.calculatorDisplayLandscapeLeft.observe(this) {
			binding.cvLeft?.setCurrentText(it)
		}
		viewModelLandscape.calculatorPreviewLandscapeLeft.observe(this) {
			binding.cvLeft?.setPreviewText(it)
		}

		viewModelLandscape.calculatorDisplayLandscapeRight.observe(this) {
			binding.cvRight?.setCurrentText(it)
		}
		viewModelLandscape.calculatorPreviewLandscapeRight.observe(this) {
			binding.cvRight?.setPreviewText(it)
		}

		viewModelLandscape.shouldShowShiftButtonToRight.observe(this) {
			if (it) {
				binding.btnShiftRight?.visibility = View.VISIBLE
			} else {
				binding.btnShiftRight?.visibility = View.INVISIBLE
			}
		}

		viewModelLandscape.shouldShowShiftButtonToLeft.observe(this) {
			if (it) {
				binding.btnShiftLeft?.visibility = View.VISIBLE
			} else {
				binding.btnShiftLeft?.visibility = View.INVISIBLE
			}
		}
	}

}