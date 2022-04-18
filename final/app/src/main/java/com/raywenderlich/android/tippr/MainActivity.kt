/*
 * Copyright (c) 2022 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 * 
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.tippr

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

  private lateinit var billAmount: EditText
  private lateinit var tipPercent: EditText
  private lateinit var tipAmount: TextView
  private lateinit var totalAmount: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    billAmount = findViewById(R.id.billAmount)
    tipPercent = findViewById(R.id.tipPercent)
    tipAmount = findViewById(R.id.tipAmount)
    totalAmount = findViewById(R.id.totalAmount)

    billAmount.setOnKeyListener { _, _, _ ->
      updateCalculations()
      false
    }
    tipPercent.setOnKeyListener { _, _, _ ->
      updateCalculations()
      false
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)

    outState.putString("billAmount", billAmount.text.toString())
    outState.putString("tipPercent", tipPercent.text.toString())
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)

    billAmount.setText(savedInstanceState.getString("billAmount", getString(R.string
        .bill_amount_default)))
    tipPercent.setText(savedInstanceState.getString("tipPercent", getString(R.string
        .tip_percent_default)))
    updateCalculations()
  }

  private fun updateCalculations() {
    val billAmount = billAmount.text.toString().toFloatOrNull()
    val tipPercent = tipPercent.text.toString().toIntOrNull()?.div(100f)

    if (billAmount == null || tipPercent == null) {
      return
    }

    tipAmount.text = getString(R.string.tip_amount_format, tipPercent * billAmount)
    totalAmount.text = getString(R.string.total_amount_format, (1 + tipPercent) *
        billAmount)
  }
}
