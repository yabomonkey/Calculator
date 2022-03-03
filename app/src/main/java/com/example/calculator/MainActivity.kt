package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: CalculatorViewModel by viewModels()
        viewModel.result.observe(this, Observer<String> { stringResult -> result.setText(stringResult) })
        viewModel.newNumber.observe(this, Observer<String> { stringNumber -> newNumber.setText(stringNumber) })
        viewModel.operation.observe(this, Observer<String> { stringOperation -> operation.text = stringOperation})

        val listener = View.OnClickListener { v ->
            viewModel.digitPressed((v as Button).text.toString())
        }

        val dotListener = View.OnClickListener {
            viewModel.dotPressed()
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
            viewModel.signPressed()
        }

        buttonSign.setOnClickListener(signListener)

        buttonClear.setOnClickListener {
            viewModel.clearPressed()
        }

    }
}