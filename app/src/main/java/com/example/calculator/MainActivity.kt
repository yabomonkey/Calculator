package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listener = View.OnClickListener { v ->
            viewModel.digitPressed(v as Button).text.toString()
        }

        val dotListener = View.OnClickListener { v ->
            val b = v as Button
            val holder = newNumber.text.toString()
            if (!holder.contains(".")) {
                newNumber.append(b.text)
            }
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(dotListener)

        val opListener = View.OnClickListener { view ->
            viewModel.operandPressed((view as Button).text.toString())
        }

        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)

        val signListener = View.OnClickListener {
            if (newNumber.text.isEmpty()){
                newNumber.append("-")
            } else {
                try {
                    val changedNewNumber = newNumber.text.toString().toDouble()
                    newNumber.setText((-changedNewNumber).toString())
                } catch (e: java.lang.NumberFormatException) {
                    //newNumber was "-" or "."
                }
            }

        }

        buttonSign.setOnClickListener(signListener)

        buttonClear.setOnClickListener {
            newNumber.setText("")
            null.also { operand1 = it }
            result.setText("")
        }

    }
}