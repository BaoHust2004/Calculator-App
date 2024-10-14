package com.example.calculatorlinear

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView

    private var state: Int = 1 // 1: nhập số thứ nhất, 2: nhập số thứ hai
    private var op: Int = 0 // 0: chưa có phép toán, 1: phép cộng, 2: phép trừ, 3: phép nhân, 4: phép chia
    private var op1: String = "" // số thứ nhất dưới dạng String
    private var op2: String = "" // số thứ hai dưới dạng String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Đặt layout cho Activity

        tvResult = findViewById(R.id.tvResult)

        // Thiết lập sự kiện cho các nút
        setButtonListeners()
    }

    private fun setButtonListeners() {
        val buttonIds = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEquals, R.id.btnC, R.id.btnCE, R.id.btnBS, R.id.btnDot
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it) }
        }
    }

    private fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btn0 -> addDigit(0)
            R.id.btn1 -> addDigit(1)
            R.id.btn2 -> addDigit(2)
            R.id.btn3 -> addDigit(3)
            R.id.btn4 -> addDigit(4)
            R.id.btn5 -> addDigit(5)
            R.id.btn6 -> addDigit(6)
            R.id.btn7 -> addDigit(7)
            R.id.btn8 -> addDigit(8)
            R.id.btn9 -> addDigit(9)
            R.id.btnDot -> addDot() // Thêm chức năng cho nút dấu '.'
            R.id.btnPlus -> setOperation(1) // Phép cộng
            R.id.btnMinus -> setOperation(2) // Phép trừ
            R.id.btnMultiply -> setOperation(3) // Phép nhân
            R.id.btnDivide -> setOperation(4) // Phép chia
            R.id.btnEquals -> calculateResult()
            R.id.btnC -> clearCurrentInput()
            R.id.btnCE -> resetCalculator()
            R.id.btnBS -> backspace()
        }
    }

    private fun setOperation(operation: Int) {
        if (state == 1 && op != 0 && op2.isEmpty()) {
            op = operation
            return
        }

        if (state == 2) {
            calculateResult()
        }

        op = operation
        state = 2 // Chuyển sang nhập số thứ hai
    }

    private fun addDigit(c: Int) {
        if (state == 1) {
            op1 += c.toString() // Cộng dồn ký tự vào số thứ nhất
            tvResult.text = op1
        } else {
            op2 += c.toString() // Cộng dồn ký tự vào số thứ hai
            tvResult.text = op2
        }
    }

    private fun addDot() {
        if (state == 1 && !op1.contains(".")) {
            op1 += "." // Thêm dấu '.' vào số thứ nhất
            tvResult.text = op1
        } else if (state == 2 && !op2.contains(".")) {
            op2 += "." // Thêm dấu '.' vào số thứ hai
            tvResult.text = op2
        }
    }

    private fun calculateResult() {
        // Nếu chưa có phép toán thì không làm gì cả
        if (op == 0) return

        val number1 = op1.toDoubleOrNull() ?: 0.0
        val number2 = op2.toDoubleOrNull() ?: 0.0

        val result = when (op) {
            1 -> number1 + number2 // Cộng
            2 -> number1 - number2 // Trừ
            3 -> number1 * number2 // Nhân
            4 -> if (number2 != 0.0) number1 / number2 else Double.NaN // Chia
            else -> 0.0
        }

        tvResult.text = result.toString()

        // Gán kết quả cho số thứ nhất và giữ lại phép toán để tiếp tục tính toán
        op1 = result.toString()
        op2 = ""
        op = 0 // Đặt lại phép toán để cho phép thực hiện phép toán tiếp theo
        state = 1 // Quay lại trạng thái nhập số thứ nhất
    }

    private fun resetCalculator() {
        state = 1
        op1 = ""
        op2 = ""
        op = 0
        tvResult.text = "0" // Đặt lại kết quả hiển thị
    }

    private fun clearCurrentInput() {
        if (state == 1) {
            op1 = ""
        } else {
            op2 = ""
        }
        tvResult.text = "0" // Đặt lại kết quả hiển thị
    }

    private fun backspace() {
        if (state == 1 && op1.isNotEmpty()) {
            op1 = op1.substring(0, op1.length - 1) // Xóa ký tự cuối trong số thứ nhất
            tvResult.text = if (op1.isEmpty()) "0" else op1
        } else if (state == 2 && op2.isNotEmpty()) {
            op2 = op2.substring(0, op2.length - 1) // Xóa ký tự cuối trong số thứ hai
            tvResult.text = if (op2.isEmpty()) "0" else op2
        }
    }
}
