@file:Suppress("DEPRECATION")

package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    //объявляем переменные
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
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

        //инициализируем переменные
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        timeLeftEt = findViewById(R.id.timeLeftEt)
        timeRightEt = findViewById(R.id.timeRightEt)
        addButton = findViewById(R.id.addButton)
        subButton = findViewById(R.id.subButton)
        result = findViewById(R.id.result)

        //по умолчанию цвет текста вывода результата - черный
        result.setTextColor(getResources().getColor(R.color.black))
    }

    //добавляем меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //прописываем поведение меню
    @SuppressLint("SetTextI18n")    //аннотация чтобы избежать warning при очистке вывода
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.resetMenuMain -> {
                timeLeftEt.text.clear()
                timeRightEt.text.clear()
                result.text = ""
                Toast.makeText(applicationContext, "Данные очищены", Toast.LENGTH_LONG).show()
                result.setTextColor(getResources().getColor(R.color.black))
            }

            R.id.exitMenuMain -> {
                finish()
                Toast.makeText(applicationContext, "Приложение закрыто", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //перевод строки ввода в секунды
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

    //перевод секунд в строку для вывода
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
        output(Actions.ADD)
    }

    fun sub(view: View) {
        output(Actions.SUB)
    }

    private fun output(action: Actions) {
        //устанавливаем цвет текста при выводе результата
        //при вычитании возьмем по модулю чтобы результат всегда был
        result.setTextColor(getResources().getColor(R.color.resultTextColor))
        if (action == Actions.ADD) result.text =
            secondsToTxt(txtToSeconds(timeLeftEt.text) + txtToSeconds(timeRightEt.text))
        else if (action == Actions.SUB) result.text =
            secondsToTxt(abs(txtToSeconds(timeLeftEt.text) - txtToSeconds(timeRightEt.text)))
        Toast.makeText(applicationContext, "Результат: ${result.text}", Toast.LENGTH_LONG).show()
    }
}