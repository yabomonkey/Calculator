package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigDecimal

class BigDecimalViewModel : ViewModel() {

    //Variables to hold the operands and type of calculation
    private var operand1: BigDecimal? = null
    private var pendingOperation = "="

    private val result = MutableLiveData<BigDecimal>()
    val stringResult: LiveData<BigDecimal> by lazy { result }

    private val newNumber = MutableLiveData<String>()
    val stringNewNumber: LiveData<String> by lazy { newNumber }

    private val operation = MutableLiveData<String>()
    val stringOperation: LiveData<String> by lazy { operation }

    fun digitPressed(caption: String) {
        if (newNumber.value != null) {
            newNumber.value = newNumber.value + caption
        } else {
            newNumber.value = caption
        }
    }

    fun operandPressed(op: String) {
        try {
            val value = newNumber.value?.toBigDecimal()
            if (value != null) {
                performOperation(value)
            }
        } catch (e: NumberFormatException) {
            newNumber.value = ""
        }
        pendingOperation = op
        operation.value = pendingOperation
    }

    fun signPressed() {
        val value = newNumber.value

        if (value == null || value.isEmpty()){
            newNumber.value = "-"
        } else {
            try {
                val changedNewNumber = value.toBigDecimal()
                newNumber.value = (-changedNewNumber).toString()
            } catch (e: java.lang.NumberFormatException) {
                //newNumber was "-" or "."
            }
        }
    }

    fun dotPressed() {
        val holder = newNumber.value.toString()
        if (!holder.contains(".")) {
            newNumber.value = "$holder."
        }
    }

    fun clearPressed() {
        newNumber.value = ""
        operand1 = null
        result.value = BigDecimal.valueOf(0.0)
        operation.value = ""
    }

    private fun performOperation(value: BigDecimal) {
        if (operand1 == null) {
            operand1 = value
        } else {

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == BigDecimal.valueOf(0.0)) {
                    BigDecimal.valueOf(Double.NaN)  //handle attempt to divide by zero
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.value = operand1
        newNumber.value = ""
    }
}