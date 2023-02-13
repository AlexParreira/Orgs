package br.com.alex.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alex.orgs.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend  fun salva(usuario: Usuario)


    @Query("""
        SELECT * FROM Usuario
        WHERE id = :usuarioId 
        AND senha =:senha""")
    suspend fun autentica(
        usuarioId: String,
        senha: String
    ): Usuario?

    @Query("""
        SELECT * FROM Usuario
        WHERE id = :usuarioId""")
    fun buscaPorID(usuarioId: String): Flow<Usuario>

}