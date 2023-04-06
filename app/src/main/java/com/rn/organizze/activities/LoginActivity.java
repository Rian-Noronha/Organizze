package com.rn.organizze.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.rn.organizze.R;
import com.rn.organizze.config.ConfiguracaoFirebase;
import com.rn.organizze.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmailLogin, editSenhaLogin;
    private Button btnLogar;

    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmailLogin      = findViewById(R.id.editEmailLogin);
        editSenhaLogin      = findViewById(R.id.editSenhaLogin);
        btnLogar            = findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmailLogin.getText().toString();
                String senha = editSenhaLogin.getText().toString();

                if(validarCampos(email, senha)){
                    usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    validarLogin();

                }else{

                }

            }
        });
    }

    public void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                }else{
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch(FirebaseAuthInvalidUserException e){
                        excecao = "Usuário não encontrado:/";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "E-mail e senha não cadastrados:/";
                    }catch(Exception e){
                        excecao = "Erro ao fazer login:/";
                        e.printStackTrace();
                    }

                    mostrarToast(excecao);
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
        finish();
    }

    public boolean validarCampos(String email, String senha){
        boolean camposValidados = false;

        if(!email.isEmpty() && email != null){
            if(!senha.isEmpty() && senha != null){
                camposValidados = true;
            }else{
                mostrarToast("Bote a senha direitinho:)");
            }
        }else{
            mostrarToast("Bote o email direitinho:)");
        }


        return camposValidados;

    }

    public void mostrarToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



}