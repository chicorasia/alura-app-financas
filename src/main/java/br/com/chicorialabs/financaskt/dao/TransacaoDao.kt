package br.com.chicorialabs.financaskt.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.chicorialabs.financaskt.model.Transacao

@Dao
interface TransacaoDao {

    @Insert
    suspend fun adiciona(transacao: Transacao)

    @Update
    suspend fun altera(transacao: Transacao)

    @Query("DELETE FROM table_transacoes WHERE transacaoId = :id")
    suspend fun remove(id: Long)

    @Query("SELECT * FROM table_transacoes")
    fun todas() : LiveData<List<Transacao>>

    @Query("SELECT * FROM table_transacoes WHERE transacaoId = :id")
    suspend fun busca(id: Long) : Transacao?


}