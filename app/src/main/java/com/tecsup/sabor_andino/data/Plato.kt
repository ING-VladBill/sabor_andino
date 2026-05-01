package com.tecsup.sabor_andino.data

data class Plato(
    val id: Int,
    val nombre: String,
    val descripcionCorta: String,
    val descripcionCompleta: String,
    val precio: Double,
    val categoria: String,
    val emoji: String,
    val imagenRes: Int
)