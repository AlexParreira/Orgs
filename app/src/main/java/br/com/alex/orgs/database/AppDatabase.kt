package br.com.alex.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alex.orgs.database.converter.Converter
import br.com.alex.orgs.database.dao.ProdutoDao
import br.com.alex.orgs.database.dao.UsuarioDao
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.model.Usuario


@Database(
    entities = [Produto::class, Usuario::class], version = 3, exportSchema = true
)

@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    abstract fun usuarioDao(): UsuarioDao


    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instacia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context, AppDatabase::class.java, "orgs.db"
            ).addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3
            ).build().also {
                db = it
            }
        }
    }
}