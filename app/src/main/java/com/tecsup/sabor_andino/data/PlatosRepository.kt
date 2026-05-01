package com.tecsup.sabor_andino.data

import com.tecsup.sabor_andino.R

object PlatosRepository {

    val platos = listOf(
        Plato(1, "Trucha a la Parrilla", "Fresca de la sierra",
            "Trucha fresca a la parrilla, sazonada con hierbas andinas, acompañada de papa nativa y ensalada.",
            32.0, "Entrada", "🐟", R.drawable.img_trucha),

        Plato(2, "Papa a la Huancaína", "Clásico andino",
            "Papa amarilla bañada en cremosa salsa de ají amarillo y queso fresco, acompañada de huevo y aceituna.",
            20.0, "Entrada", "🥔", R.drawable.img_papa_huancaina),

        Plato(3, "Pachamanca", "Tradición ancestral",
            "Carnes marinadas cocidas bajo tierra con piedras calientes, acompañadas de papas, habas y choclo.",
            60.0, "Plato de Fondo", "🍖", R.drawable.img_pachamanca),

        Plato(4, "Cuy Chactado", "Crujiente y tradicional",
            "Cuy frito crocante acompañado de papas doradas y ensalada fresca.",
            55.0, "Plato de Fondo", "🐹", R.drawable.img_cuy_chactado),

        Plato(5, "Mazamorra de Quinua", "Postre nutritivo",
            "Dulce tradicional preparado con quinua, leche, canela y clavo de olor.",
            15.0, "Postre", "🍚", R.drawable.img_mazamorra_quinua),

        Plato(6, "Mate de Coca", "Infusión andina",
            "Bebida caliente tradicional de hojas de coca, ideal para la digestión y el frío.",
            8.0, "Bebida", "🍵", R.drawable.img_mate_coca)
    )

    fun getById(id: Int): Plato? = platos.find { it.id == id }
}