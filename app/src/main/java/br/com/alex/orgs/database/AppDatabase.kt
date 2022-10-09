package br.com.alex.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alex.orgs.database.converter.Converter
import br.com.alex.orgs.database.dao.ProdutoDao
import br.com.alex.orgs.model.Produto

@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
}