package com.rn.organizze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.rn.organizze.R;
import com.rn.organizze.config.ConfiguracaoFirebase;

public class MainActivity extends IntroActivity {
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);



        setButtonNextVisible(false);
        setButtonBackVisible(false);

        addSlide(new FragmentSlide.Builder()

                .background(R.color.white)
                .fragment(R.layout.intro_1)
                .build()

        );

        addSlide(new FragmentSlide.Builder()

                .background(R.color.white)
                .fragment(R.layout.intro_2)
                .build()

        );

        addSlide(new FragmentSlide.Builder()

                .background(R.color.white)
                .fragment(R.layout.intro_3)
                .build()

        );

        addSlide(new FragmentSlide.Builder()

                .background(R.color.white)
                .fragment(R.layout.intro_4)
                .build()
        );

        addSlide(new FragmentSlide.Builder()

                .background(R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build()

        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //autenticacao.signOut();
        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }

    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
    }


    public void irAhTelaLogin(View view){
       startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void irAhTelaCadastro(View view){
        startActivity(new Intent(MainActivity.this, CadastroActivity.class));
    }
}