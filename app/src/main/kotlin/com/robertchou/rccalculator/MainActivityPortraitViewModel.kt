package com.robertchou.rccalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robertchou.module.calculator.CalculatorModel

class MainActivityPortraitViewModel:ViewModel() {

    private val _calculatorDisplayPortrait = MutableLiveData("")
    val calculatorDisplayPortrait: LiveData<String> get() = _calculatorDisplayPortrait

    private val _calculatorPreviewPortrait = MutableLiveData("")
    val calculatorPreviewPortrait: LiveData<String> get() = _calculatorPreviewPortrait

    private val calculatorModelPortrait: CalculatorModel by lazy { CalculatorModel() }

    fun clickPortraitButton(input:String){
        calculatorModelPortrait.inputText(input)
        _calculatorDisplayPortrait.value = calculatorModelPortrait.getLastDisplay()
        _calculatorPreviewPortrait.value = calculatorModelPortrait.getCalculateProcess()
    }

}