package com.example.viacepg

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cepTextView = findViewById<TextView>(R.id.cepTv)
        val cidadeTextView = findViewById<TextView>(R.id.cidadeTv)

        val cep = intent.getStringExtra("CEP_EXTRA")

        if (cep != null) {
            val viaCepApi = RetrofitHelper.getInstance().create(ViaCepApi::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = viaCepApi.getEnderecos(cep)
                    if (response.isSuccessful) {
                        val endereco = response.body()
                        Log.d("Retorno da API: ", endereco.toString())

                        // Alterna para a Main Thread para atualizar a UI
                        withContext(Dispatchers.Main) {
                            cepTextView.text = endereco?.cep
                            cidadeTextView.text = endereco?.localidade
                        }
                    } else {
                        // Lida com erros da API
                        withContext(Dispatchers.Main) {
                            cepTextView.text = "Erro: ${response.code()}"
                        }
                    }
                } catch (e: Exception) {
                    // Lida com exceções de rede ou outras
                    withContext(Dispatchers.Main) {
                        cepTextView.text = "Ocorreu um erro: ${e.message}"
                    }
                }
            }
        } else {
            cepTextView.text = "CEP não encontrado."
        }
    }
}