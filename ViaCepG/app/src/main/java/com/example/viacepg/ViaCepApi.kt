package com.example.viacepg

import com.example.viacepg.model.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApi {
    @GET("ws/{cep}/json")
    suspend fun getEnderecos(@Path("cep") year: String) : Response<Endereco>
}