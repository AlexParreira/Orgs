package br.com.alex.orgs.database.dao

import androidx.room.*
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.model.Usuario
import kotlinx.coroutines.flow.Flow


@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos():Flow<List<Produto>>

    @Query("SELECT * FROM Produto WHERE usuarioID = :usuarioID")
    fun buscaTodosDoUsuario(usuarioID: String): Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva (vararg produto: Produto)

    @Delete
    suspend fun remove(produto: Produto)

    @Update
   suspend fun atualiza(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscarPorId(id: Long) : Flow<Produto?>

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun buscaTodosOrdenadorPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun buscaTodosOrdenadorPorNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    fun buscaTodosOrdenadorPorDescricaoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    fun buscaTodosOrdenadorPorDescricaoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    fun buscaTodosOrdenadorPorValorAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    fun buscaTodosOrdenadorPorValorDesc(): List<Produto>
}