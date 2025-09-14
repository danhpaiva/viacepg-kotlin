package com.example.viacepg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cepEditText = findViewById<EditText>(R.id.cepEditText)
        val searchButton = findViewById<Button>(R.id.searchButton)

        searchButton.setOnClickListener {
            val cep = cepEditText.text.toString().trim()

            if (cep.isNotEmpty()) {
                val intent = Intent(this, ResultadoActivity::class.java)

                intent.putExtra("CEP_EXTRA", cep)

                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, digite um CEP.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}