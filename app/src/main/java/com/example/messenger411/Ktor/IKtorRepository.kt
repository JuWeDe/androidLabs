package com.example.messenger411.Ktor

import com.example.messenger411.Data.Hero


interface IKtorRepository {
    suspend fun getHeroes(number: Int): List<Hero>
}


