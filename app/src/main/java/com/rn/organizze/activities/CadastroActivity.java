package com.rn.organizze.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.rn.organizze.R;
import com.rn.organizze.config.ConfiguracaoFirebase;
import com.rn.organizze.helper.Base64Custom;
import com.rn.organizze.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button buttonCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome       = findViewById(R.id.editNome);
        campoEmail      = findViewById(R.id.editEmail);
        campoSenha      = findViewById(R.id.editSenha);
        buttonCadastrar = findViewById(R.id.btnCadastrar);


        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome     = campoNome.getText().toString();
                String email    = campoEmail.getText().toString();
                String senha    = campoSenha.getText().toString();

                if(validarCampos(nome, email, senha)){
                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    cadastrarUsuario(usuario);
                }else{
                    mostrarToast("Se ligue.");
                }

            }
        });

    }


    public void cadastrarUsuario(Usuario  usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();

                    finish();
                }else{

                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        excecao = "Senha fraquinha, tente uma mais forte:)";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Bote um email válido:)";
                    }catch(FirebaseAuthUserCollisionException e){
                        excecao = "Conta já colocada, bote outra:)";
                    }catch(Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }


                    mostrarToast(excecao);
                }
            }
        });

    }

    public boolean validarCampos(String nome, String email, String senha){

        boolean camposValidados = false;

        if(!nome.isEmpty() && nome != null){
            if(!email.isEmpty() && email != null){
                if(!senha.isEmpty() && senha != null){
                    camposValidados = true;
                }else{
                    mostrarToast("Preencha o campo senha direitinho:)");
                }
            }else{
                mostrarToast("Preencha o campo email direitinho:)");
            }
        }else{
            mostrarToast("Preencha o campo nome direitinho:)");
        }

        return camposValidados;

    }


    public void mostrarToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


}