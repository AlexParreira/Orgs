package br.com.alex.orgs.database.dao

import androidx.room.*
import br.com.alex.orgs.model.Produto


@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos():List<Produto>

    @Insert
    fun salva(vararg produto: Produto)

    @Delete
    fun remove(produto: Produto)

    @Update
    fun atualiza(produto: Produto)


    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buxarPorId(id: Long) :Produto?
}