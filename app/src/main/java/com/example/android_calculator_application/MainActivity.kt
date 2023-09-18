package com.example.android_calculator_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import java.util.Stack
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private var calcVal: String = "0"
    private var valStack: Stack<String> = Stack()
    private var operands: String = ""

    // Primary Buttons
    private lateinit var oneBtn: Button
    private lateinit var twoBtn: Button
    private lateinit var threeBtn: Button
    private lateinit var fourBtn: Button
    private lateinit var fiveBtn: Button
    private lateinit var sixBtn: Button
    private lateinit var sevenBtn: Button
    private lateinit var eightBtn: Button
    private lateinit var nineBtn: Button
    private lateinit var zeroBtn: Button
    private lateinit var dotBtn: Button
    private lateinit var multiplyBtn: Button
    private lateinit var divideBtn: Button
    private lateinit var plusBtn: Button
    private lateinit var subtractButton: Button
    private lateinit var sqrtButton: Button
    private lateinit var equalButton: Button
    private lateinit var resultField: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialization
        resultField = findViewById(R.id.result)
        resultField.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                calcVal = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        // Basic button Functionality
        oneBtn = findViewById(R.id.button1)
        oneBtn.setOnClickListener { buttonAddValue("1") }
        twoBtn = findViewById(R.id.button2)
        twoBtn.setOnClickListener{ buttonAddValue("2") }
        threeBtn = findViewById(R.id.button3)
        threeBtn.setOnClickListener{ buttonAddValue("3") }
        fourBtn = findViewById(R.id.button4)
        fourBtn.setOnClickListener{ buttonAddValue("4") }
        fiveBtn = findViewById(R.id.button5)
        fiveBtn.setOnClickListener{ buttonAddValue("5") }
        sixBtn = findViewById(R.id.button6)
        sixBtn.setOnClickListener{ buttonAddValue("6") }
        sevenBtn = findViewById(R.id.button7)
        sevenBtn.setOnClickListener{ buttonAddValue("7") }
        eightBtn = findViewById(R.id.button8)
        eightBtn.setOnClickListener{ buttonAddValue("8") }
        nineBtn = findViewById(R.id.button9)
        nineBtn.setOnClickListener{ buttonAddValue("9") }
        zeroBtn = findViewById(R.id.button0)
        zeroBtn.setOnClickListener{ buttonAddValue("0") }

        divideBtn = findViewById(R.id.buttonDivide)
        divideBtn.setOnClickListener{ divideNum() }
        sqrtButton = findViewById(R.id.buttonSqrt)
        sqrtButton.setOnClickListener{ sqrtNum() }
        multiplyBtn = findViewById(R.id.buttonTimes)
        multiplyBtn.setOnClickListener{ timesNum() }
        plusBtn = findViewById(R.id.buttonPlus)
        plusBtn.setOnClickListener{ addNum() }
        subtractButton = findViewById(R.id.buttonMinus)
        subtractButton.setOnClickListener{ minusNum() }
        dotBtn = findViewById(R.id.buttonDot)
        dotBtn.setOnClickListener{ dotFunction() }
        equalButton = findViewById(R.id.buttonEqual)
        equalButton.setOnClickListener{ equalNum() }
    }

    // Dot Function
    private fun dotFunction() {
        if (this.calcVal.indexOf('.') == -1) {
            this.calcVal += "."
        }
        this.resultField.setText(this.calcVal)
    }

    // Appending value for any given number / regular number callback
    private fun buttonAddValue(addVal: String) {
        if (this.calcVal == "0") this.calcVal = ""
        this.calcVal += addVal
        this.resultField.setText(this.calcVal)
    }

    // Function for adding Button
    private fun sqrtNum() {
        this.operands = "sqrt"
        this.calcVal = sqrt(this.calcVal.toDouble()).toString()
        this.resultField.setText(this.calcVal)
    }

    private fun addNum() {
        this.operands = "+"
        if (!this.valStack.isEmpty()) {
            this.valStack.push((this.valStack.pop().toDouble() + this.calcVal.toDouble()).toString())
            this.resultField.setText(this.valStack.peek())
        } else {
            this.valStack.add(this.calcVal)
            this.resultField.setText("0")
        }
        this.calcVal = "0"
    }

    private fun minusNum() {
        this.operands = "-"
        if (!this.valStack.isEmpty()) {
            this.valStack.push((this.valStack.pop().toDouble() - this.calcVal.toDouble()).toString())
            this.resultField.setText(this.valStack.peek())
        } else {
            this.valStack.add(this.calcVal)
            this.resultField.setText("0")
        }
        this.calcVal = "0"
    }

    private fun timesNum() {
        this.operands = "*"
        if (!this.valStack.isEmpty()) {
            this.valStack.push((this.valStack.pop().toDouble() * this.calcVal.toDouble()).toString())
            this.resultField.setText(this.valStack.peek())
        } else {
            this.valStack.add(this.calcVal)
            this.resultField.setText("0")
        }
        this.calcVal = "0"
    }

    private fun divideNum() {
        this.operands = "/"
        if (!this.valStack.isEmpty()) {
            if (this.calcVal == "0") return
            this.valStack.push((this.valStack.pop().toDouble() / this.calcVal.toDouble()).toString())
            this.resultField.setText(this.valStack.peek())
        } else {
            this.valStack.add(this.calcVal)
            this.resultField.setText("0")
        }
        this.calcVal = "0"
    }

    private fun equalNum() {
        if (this.valStack.isEmpty()) return

        when (this.operands) {
            "+" -> {
                addNum()
            }
            "-" -> {
                minusNum()
            }
            "*" -> {
                timesNum()
            }
            "sqrt" -> {
                sqrtNum()
            }
            else -> {
                divideNum()
            }
        }
        this.valStack = Stack()
        this.calcVal = "0"
    }
}