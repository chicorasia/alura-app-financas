package br.com.chicorialabs.financaskt.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "table_transacoes")
data class Transacao(
    @PrimaryKey(autoGenerate = true)
    val transacaoId: Long = 0L,
    @ColumnInfo(name = "valor")
    val valor: BigDecimal,
    @ColumnInfo(name = "categoria")
    val categoria: String = "Indefinida",
    @ColumnInfo(name = "tipo")
    val tipo: Tipo,
    @ColumnInfo(name = "data")
    val data: Calendar = Calendar.getInstance()
)