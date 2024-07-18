package com.robertchou.rccalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robertchou.module.calculator.CalculatorModel

class MainActivityLandViewModel:ViewModel() {

    private val _shouldShowShiftButtonToRight = MutableLiveData(false)
    val shouldShowShiftButtonToRight: LiveData<Boolean> get() = _shouldShowShiftButtonToRight

    private val _shouldShowShiftButtonToLeft = MutableLiveData(false)
    val shouldShowShiftButtonToLeft: LiveData<Boolean> get() = _shouldShowShiftButtonToLeft


    private val _calculatorDisplayLandscapeLeft = MutableLiveData("")
    val calculatorDisplayLandscapeLeft: LiveData<String> get() = _calculatorDisplayLandscapeLeft

    private val _calculatorPreviewLandscapeLeft = MutableLiveData("")
    val calculatorPreviewLandscapeLeft: LiveData<String> get() = _calculatorPreviewLandscapeLeft

    private val _calculatorDisplayLandscapeRight = MutableLiveData("")
    val calculatorDisplayLandscapeRight: LiveData<String> get() = _calculatorDisplayLandscapeRight

    private val _calculatorPreviewLandscapeRight = MutableLiveData("")
    val calculatorPreviewLandscapeRight: LiveData<String> get() = _calculatorPreviewLandscapeRight

    private val calculatorModelLeft:CalculatorModel by lazy { CalculatorModel() }
    private val calculatorModelRight:CalculatorModel by lazy { CalculatorModel() }

    fun clickLeftButton(input:String){
        calculatorModelLeft.inputText(input)
        _calculatorDisplayLandscapeLeft.value = calculatorModelLeft.getLastDisplay()
        _calculatorPreviewLandscapeLeft.value = calculatorModelLeft.getCalculateProcess()
        _shouldShowShiftButtonToRight.value = calculatorModelLeft.isCanShift() && calculatorModelRight.isCanReceive()
        _shouldShowShiftButtonToLeft.value = calculatorModelRight.isCanShift() && calculatorModelLeft.isCanReceive()
    }

    fun clickRightButton(input:String){
        calculatorModelRight.inputText(input)
        _calculatorDisplayLandscapeRight.value = calculatorModelRight.getLastDisplay()
        _calculatorPreviewLandscapeRight.value = calculatorModelRight.getCalculateProcess()
        _shouldShowShiftButtonToRight.value = calculatorModelLeft.isCanShift() && calculatorModelRight.isCanReceive()
        _shouldShowShiftButtonToLeft.value = calculatorModelRight.isCanShift() && calculatorModelLeft.isCanReceive()
    }

    fun shiftLeftToRight(){
        calculatorModelLeft.getLastCalculateItem()?.let {
            calculatorModelRight.insertShiftNumber(it)
        }
        _calculatorDisplayLandscapeLeft.value = calculatorModelLeft.getLastDisplay()
        _calculatorPreviewLandscapeLeft.value = calculatorModelLeft.getCalculateProcess()
        _calculatorDisplayLandscapeRight.value = calculatorModelRight.getLastDisplay()
        _calculatorPreviewLandscapeRight.value = calculatorModelRight.getCalculateProcess()
    }

    fun shiftRightToLeft(){
        calculatorModelRight.getLastCalculateItem()?.let {
            calculatorModelLeft.insertShiftNumber(it)
        }

        _calculatorDisplayLandscapeLeft.value = calculatorModelLeft.getLastDisplay()
        _calculatorPreviewLandscapeLeft.value = calculatorModelLeft.getCalculateProcess()
        _calculatorDisplayLandscapeRight.value = calculatorModelRight.getLastDisplay()
        _calculatorPreviewLandscapeRight.value = calculatorModelRight.getCalculateProcess()
    }

}