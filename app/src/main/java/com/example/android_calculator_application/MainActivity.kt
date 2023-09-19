package com.example.android_calculator_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import java.lang.Exception
import java.util.Stack
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private var calcVal: String = "0"
    private lateinit var resultField: EditText
    private var operands: String = ""
    private var valStack: Stack<String> = Stack()

    // Initialize buttons
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialization
        resultField = findViewById(R.id.result)
        resultField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                calcVal = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        // Initialize buttons and set click listeners
        initializeButtons()
    }

    private fun initializeButtons() {
        // Initialize buttons here
        oneBtn = findViewById(R.id.button1)
        twoBtn = findViewById(R.id.button2)
        threeBtn = findViewById(R.id.button3)
        fourBtn = findViewById(R.id.button4)
        fiveBtn = findViewById(R.id.button5)
        sixBtn = findViewById(R.id.button6)
        sevenBtn = findViewById(R.id.button7)
        eightBtn = findViewById(R.id.button8)
        nineBtn = findViewById(R.id.button9)
        zeroBtn = findViewById(R.id.button0)
        dotBtn = findViewById(R.id.buttonDot)
        multiplyBtn = findViewById(R.id.buttonTimes)
        divideBtn = findViewById(R.id.buttonDivide)
        plusBtn = findViewById(R.id.buttonPlus)
        subtractButton = findViewById(R.id.buttonMinus)
        sqrtButton = findViewById(R.id.buttonSqrt)
        equalButton = findViewById(R.id.buttonEqual)

        // Set click listeners
        oneBtn.setOnClickListener { buttonAddValue("1") }
        twoBtn.setOnClickListener { buttonAddValue("2") }
        threeBtn.setOnClickListener { buttonAddValue("3") }
        fourBtn.setOnClickListener { buttonAddValue("4") }
        fiveBtn.setOnClickListener { buttonAddValue("5") }
        sixBtn.setOnClickListener { buttonAddValue("6") }
        sevenBtn.setOnClickListener { buttonAddValue("7") }
        eightBtn.setOnClickListener { buttonAddValue("8") }
        nineBtn.setOnClickListener { buttonAddValue("9") }
        zeroBtn.setOnClickListener { buttonAddValue("0") }
        dotBtn.setOnClickListener { dotFunction() }
        multiplyBtn.setOnClickListener { timesNum() }
        divideBtn.setOnClickListener { divideNum() }
        plusBtn.setOnClickListener { addNum() }
        subtractButton.setOnClickListener { minusNum() }
        sqrtButton.setOnClickListener { sqrtNum() }
        equalButton.setOnClickListener { equalNum() }
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
        if (this.calcVal == "0" || this.calcVal == "0.0") {
            this.calcVal = ""
        }
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
            this.valStack.push(
                (this.valStack.pop().toDouble() + this.calcVal.toDouble()).toString()
            )
            this.resultField.setText(this.valStack.peek())
        } else {
            this.valStack.add(this.calcVal)
            this.resultField.setText("0")
        }
        this.calcVal = ""
    }

    private fun minusNum() {
        this.operands = "-"
        if (!this.valStack.isEmpty()) {
            this.valStack.push(
                (this.valStack.pop().toDouble() - this.calcVal.toDouble()).toString()
            )
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
            this.valStack.push(
                (this.valStack.pop().toDouble() * this.calcVal.toDouble()).toString()
            )
            this.resultField.setText(this.valStack.peek())
        } else {
            this.valStack.add(this.calcVal)
            this.resultField.setText("0")
        }
        this.calcVal = ""
    }

    private fun divideNum() {
        this.operands = "/"
        if (!this.valStack.isEmpty()) {
            if (this.calcVal == "0") return
            this.valStack.push(
                (this.valStack.pop().toDouble() / this.calcVal.toDouble()).toString()
            )
            this.resultField.setText(this.valStack.peek())
        } else {
            this.valStack.add(this.calcVal)
            this.resultField.setText("0")
        }
        this.calcVal = "0"
    }

    private fun equalNum() {
        try {
            val expression = calcVal.replace("sqrt", "√")
            val result = evaluateExpression(expression)
            this.calcVal = result.toString()
            this.valStack.clear()
            this.resultField.setText(this.calcVal)
        } catch (e: Exception) {
            this.calcVal = "Error"
            this.resultField.setText(this.calcVal)
        }
    }

    //string parsing
    private fun evaluateExpression(expression: String): Double {
        val tokens = expression.split("(?<=[-+*/√()])|(?=[-+*/√()])".toRegex())
            .filter { it.isNotBlank() }

        val stack = Stack<String>()
        val output = mutableListOf<String>()

        val precedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2, "√" to 3)

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> {
                    output.add(token)
                }
                token == "√" -> {
                    stack.push(token)
                }
                token == "(" -> {
                    stack.push(token)
                }
                token == ")" -> {
                    while (stack.isNotEmpty() && stack.peek() != "(") {
                        output.add(stack.pop())
                    }
                    stack.pop()
                }
                else -> {
                    while (stack.isNotEmpty() && precedence.getOrDefault(token, 0) <= precedence.getOrDefault(stack.peek(), 0)) {
                        output.add(stack.pop())
                    }
                    stack.push(token)
                }
            }
        }

        while (stack.isNotEmpty()) {
            output.add(stack.pop())
        }

        val resultStack = Stack<Double>()

        for (token in output) {
            if (token.toDoubleOrNull() != null) {
                resultStack.push(token.toDouble())
            } else {
                if (token == "√") {
                    if (resultStack.isEmpty()) {
                        throw IllegalArgumentException("Invalid expression")
                    }
                    val value = resultStack.pop()
                    resultStack.push(sqrt(value))
                } else {
                    if (resultStack.size < 2) {
                        throw IllegalArgumentException("Invalid expression")
                    }
                    val operand2 = resultStack.pop()
                    val operand1 = resultStack.pop()
                    when (token) {
                        "+" -> resultStack.push(operand1 + operand2)
                        "-" -> resultStack.push(operand1 - operand2)
                        "*" -> resultStack.push(operand1 * operand2)
                        "/" -> {
                            if (operand2 == 0.0) {
                                throw ArithmeticException("Division by zero")
                            }
                            resultStack.push(operand1 / operand2)
                        }
                        else -> throw IllegalArgumentException("Invalid operator: $token")
                    }
                }
            }
        }

        if (resultStack.size != 1) {
            throw IllegalArgumentException("Invalid expression")
        }

        return resultStack.pop()
    }

}
