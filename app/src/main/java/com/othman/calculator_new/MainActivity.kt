package com.othman.calculator_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private var lastEqual: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View) {
        val value = tvInput?.text.toString()
        if (value.startsWith("0") && !value.contains(".")){
            tvInput?.text = ""
        }
            tvInput?.append((view as Button).text)
            lastNumeric = true
            lastEqual = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDot(view: View) {
        if (lastNumeric && !lastDot){
            tvInput?.append((view as Button).text)
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastDot = false
                lastNumeric = false
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        }
        else{
            value.contains("+")
                    || value.contains("-")
                    || value.contains("÷")
                    || value.contains("×")
        }
    }

    fun onEquals(view: View) {
        var value = tvInput?.text.toString()
        if (lastNumeric){
            lastEqual = true
            var prefix = ""
            try {
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }
                if (value.contains("-")) {
                    val splitValue = value.split("-")
                    var one = splitValue[0]
                    val tow = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    val result = (one.toDouble() - tow.toDouble()).toString()
                    tvInput?.text = removeZeroAfterDot(result)
                }
                else if (value.contains("+")) {
                    val splitValue = value.split("+")
                    var one = splitValue[0]
                    val tow = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    val result = (one.toDouble() + tow.toDouble()).toString()
                    tvInput?.text = removeZeroAfterDot(result)
                }
                else if (value.contains("÷")) {
                    val splitValue = value.split("÷")
                    var one = splitValue[0]
                    val tow = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    val result = (one.toDouble() / tow.toDouble()).toString()
                    tvInput?.text = removeZeroAfterDot(result)
                }
                else if (value.contains("×")) {
                    val splitValue = value.split("×")
                    var one = splitValue[0]
                    val tow = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    val result = (one.toDouble() * tow.toDouble()).toString()
                    tvInput?.text = removeZeroAfterDot(result)
                }
                } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onPlusMinus(view: View) {
        var value = tvInput?.text.toString()
        if (lastNumeric && !isOperatorAdded(value)) {
            if (!value.startsWith("-")) {
                value = "-$value"
                tvInput?.text = value
            }
            else
                tvInput?.text = value.substring(1)
        }
    }

    fun onPercent(view: View) {
        val value = tvInput?.text.toString()
        if (lastNumeric && !isOperatorAdded(value)) {
            val result = (value.toDouble() / 100).toString()
            tvInput?.text = result
        }
    }

    fun onDelete(view: View) {
        try {
              if (lastEqual) {
                  tvInput?.text = ""
                  lastNumeric = false
                  lastDot = false
                  lastEqual = false
              }
              else{
                val value = tvInput?.text.toString()
                if (value.endsWith(".")) { lastDot = false; lastNumeric = true }
                if (value.endsWith("-") || value.endsWith("+") || value.endsWith("×") || value.endsWith("÷")) { lastNumeric = true }
                tvInput?.text = value.substring(0, value.length - 1)
              }
        }catch (e:Exception){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
    }

    fun onZero(view: View) {
        val value = tvInput?.text.toString()
        if (!value.startsWith("0") || value.contains(".")){
            tvInput?.append((view as Button).text)
            lastNumeric = true
            lastEqual = false
        }


        }

}


