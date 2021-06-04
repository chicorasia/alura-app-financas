package br.com.chicorialabs.financaskt.model

import br.com.chicorialabs.financaskt.R

enum class Tipo(val cor: Int, val icone: Int, val titulo: Int, val categorias: Int) {
    RECEITA(
        R.color.receita,
        R.drawable.icone_transacao_item_receita,
        R.string.receita,
        R.array.categorias_de_receita
    ),
    DESPESA(
        R.color.despesa,
        R.drawable.icone_transacao_item_despesa,
        R.string.despesa,
        R.array.categorias_de_despesa
    )
}
//
//class Converters {
//
//    @TypeConverter
//    fun toTipo(value: String) : Tipo {
//        return when(value) {
//            "RECEITA" -> Tipo.RECEITA
//            else -> Tipo.DESPESA
//        }
//    }
//
//    @TypeConverter
//    fun fromTipo(value: Tipo) = value.name
//}
