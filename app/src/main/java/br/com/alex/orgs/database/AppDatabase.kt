package br.com.alex.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alex.orgs.database.converter.Converter
import br.com.alex.orgs.database.dao.ProdutoDao
import br.com.alex.orgs.model.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao


    companion object {
        fun instacia(context: Context):AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}