package com.example.calculator

import android.os.Bundle
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
}