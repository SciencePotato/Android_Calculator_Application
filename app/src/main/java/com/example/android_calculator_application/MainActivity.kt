package com.example.android_calculator_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private var calcVal: String = "0"
    private var valStack: Stack<String> = Stack()

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
    private lateinit var resultField: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialization
        resultField = findViewById(R.id.result)
        oneBtn = findViewById(R.id.button1)
        oneBtn.setOnClickListener {
            buttonAddValue("1")
        }
        twoBtn = findViewById(R.id.button2)
        threeBtn = findViewById(R.id.button3)
        fourBtn = findViewById(R.id.button4)
        fiveBtn = findViewById(R.id.button5)
        sixBtn = findViewById(R.id.button6)
        sevenBtn = findViewById(R.id.button7)
        eightBtn = findViewById(R.id.button8)
        nineBtn = findViewById(R.id.button9)
        zeroBtn = findViewById(R.id.button0)
        divideBtn = findViewById(R.id.buttonDivide)
        sqrtButton = findViewById(R.id.buttonSqrt)
        multiplyBtn = findViewById(R.id.buttonTimes)
        plusBtn = findViewById(R.id.buttonPlus)
        subtractButton = findViewById(R.id.buttonMinus)
        dotBtn = findViewById(R.id.buttonDot)
    }

    // Dot Function
    fun dotFunction() {
        if (this.calcVal.indexOf('.') == -1) {
            this.calcVal += "."
            this.resultField.text = this.calcVal
        }
    }

    // Appending value for any given number / regular number callback
    fun buttonAddValue(addVal: String) {
        if (this.calcVal == "0") this.calcVal = ""
        this.calcVal += addVal
        this.resultField.text = this.calcVal
    }

    // Function for adding Button
    fun addNum() {
        if (!this.valStack.isEmpty()) {
            this.valStack.push((this.valStack.pop().toDouble() + this.calcVal.toDouble()).toString())
        } else {
            this.valStack.add(this.calcVal)
        }
        this.calcVal = "0"
    }

    fun minusNum() {
        this.valStack.add(this.calcVal)
        this.calcVal = "0"
    }

    fun timesNum() {
        this.valStack.add(this.calcVal)
        this.calcVal = "0"
    }

    fun divideNum() {
        this.valStack.add(this.calcVal)
        this.calcVal = "0"
    }
}