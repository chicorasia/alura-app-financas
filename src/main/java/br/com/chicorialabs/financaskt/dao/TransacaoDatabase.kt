package br.com.chicorialabs.financaskt.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.chicorialabs.financaskt.model.Transacao

@TypeConverters(Converters::class)
@Database(
    entities = [Transacao::class],
    version = 1,
    exportSchema = false
)
abstract class TransacaoDatabase : RoomDatabase() {

    abstract val transacaoDao: TransacaoDao

    companion object {

        @Volatile
        private var INSTANCE: TransacaoDatabase? = null

        fun getInstance(context: Context) : TransacaoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        TransacaoDatabase::class.java,
                        "transacao_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}