package br.com.alex.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import br.com.alex.orgs.extensions.toast
import br.com.alex.orgs.model.Usuario
import kotlinx.coroutines.launch

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }
    private val dao by lazy {
        AppDatabase.instacia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            Log.i("CadastroUsuario", "onCreate: $novoUsuario")
            cadastro(novoUsuario)
        }
    }

    private fun cadastro(usuario: Usuario) {
        lifecycle.coroutineScope.launch() {
            try {
                dao.salva(usuario)
                finish()
            } catch (e: Exception) {
                Log.e("CadastroUsuario", "configurarBotaoCadastar", e)
                toast("Falha ao cadastrar usuário.")
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }
}