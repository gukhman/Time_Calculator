package com.example.calculator

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var timeLeftEt: EditText
    private lateinit var timeRightEt: EditText
    private lateinit var addButton: Button
    private lateinit var subButton: Button
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        timeLeftEt = findViewById(R.id.timeLeftEt)
        timeRightEt = findViewById(R.id.timeRightEt)
        addButton = findViewById(R.id.addButton)
        subButton = findViewById(R.id.subButton)
        result = findViewById(R.id.result)
    }

    private fun txtToSeconds(text: Editable): Int {
        var totalSeconds = 0

        // regex часы, минуты и секунды
        val regex = """(\d+)([hms])""".toRegex()
        val matches = regex.findAll(text)

        for (match in matches) {
            val value = match.groupValues[1].toInt()
            val hms = match.groupValues[2]

            totalSeconds += when (hms) {
                "h" -> value * 3600 // Часы в секунды
                "m" -> value * 60   // Минуты в секунды
                "s" -> value         // Секунды
                else -> 0
            }
        }

        return totalSeconds
    }

    private fun secondsToTxt(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        return buildString {
            if (hours > 0) append("${hours}h")
            if (minutes > 0) append("${minutes}m")
            if (secs > 0) append("${secs}s")
        }
    }

    fun add(view: View) {
        result.text = secondsToTxt(txtToSeconds(timeLeftEt.text) + txtToSeconds(timeRightEt.text))
    }

    //возьмем по модулю чтобы результат всегда был
    fun sub(view: View) {
        result.text = secondsToTxt(abs(txtToSeconds(timeLeftEt.text) - txtToSeconds(timeRightEt.text)))
    }
}