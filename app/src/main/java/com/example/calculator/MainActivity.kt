package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var timeLeftEt: EditText
    private lateinit var timeRightEt: EditText
    private lateinit var addButton: Button
    private lateinit var subButton: Button
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        timeLeftEt = findViewById(R.id.timeLeftEt)
        timeRightEt = findViewById(R.id.timeRightEt)
        addButton = findViewById(R.id.addButton)
        subButton = findViewById(R.id.subButton)
        result = findViewById(R.id.result)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun readEt(et: EditText): Int{
        var totalSeconds = 0

        // Регулярное выражение для поиска часов, минут и секунд в строке
        val regex = """(\d+)([hms])""".toRegex()

        // Поиск всех совпадений в строке
        val matches = regex.findAll(et.text)

        for (match in matches) {
            val value = match.groupValues[1].toInt()
            val unit = match.groupValues[2]

            totalSeconds += when (unit) {
                "h" -> value * 3600 // Часы в секунды
                "m" -> value * 60   // Минуты в секунды
                "s" -> value         // Секунды
                else -> 0
            }
        }

        return totalSeconds
    }

    fun add(view: View) {
        result.text = (readEt(timeLeftEt) + readEt(timeRightEt)).toString()
    }
}