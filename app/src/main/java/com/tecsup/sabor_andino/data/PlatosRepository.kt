package com.tecsup.sabor_andino.data

object PlatosRepository {

    val platos = listOf(
        Plato(1, "Ceviche Andino", "Ceviche con papa huayro",
            "Ceviche preparado con pescado fresco, limón, ají amarillo y papa huayro cocida. Tradicional de la sierra.",
            32.0, "Entrada", "🍋"),
        Plato(2, "Causa Rellena", "Causa de atún y palta",
            "Papa amarilla sazonada con ají amarillo, rellena de mezcla de atún, mayonesa y palta fresca.",
            28.0, "Entrada", "🥑"),
        Plato(3, "Lomo Saltado", "Clásico de la cocina peruana",
            "Tiras de lomo fino salteadas con tomate, cebolla, ají amarillo, sillao y papas fritas. Servido con arroz.",
            52.0, "Plato de Fondo", "🥩"),
        Plato(4, "Ají de Gallina", "Cremoso y tradicional",
            "Gallina desmenuzada en salsa cremosa de ají amarillo, pan, nueces y queso. Acompañado de papa y arroz.",
            45.0, "Plato de Fondo", "🍗"),
        Plato(5, "Suspiro Limeño", "Postre colonial peruano",
            "Cremoso manjar blanco cubierto con merengue de oporto y canela. Receta tradicional limeña.",
            18.0, "Postre", "🍮"),
        Plato(6, "Chicha Morada", "Bebida típica peruana",
            "Bebida refrescante preparada con maíz morado, piña, membrillo, canela y clavo de olor. Sin alcohol.",
            12.0, "Bebida", "🍇")
    )

    fun getById(id: Int): Plato? = platos.find { it.id == id }
}