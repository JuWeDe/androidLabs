package com.example.messenger411.Data

import kotlinx.serialization.Serializable

@Serializable
class Hero(
    val name: String? = null,
    val culture: String? = null,
    val born: String? = null,
    val titles: List<String>? = arrayListOf(),
    var aliases: List<String>? = arrayListOf(),
    var playedBy: List<String>? = arrayListOf()
)

