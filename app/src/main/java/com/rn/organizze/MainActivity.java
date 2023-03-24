package com.rn.organizze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

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

    public void irAhTelaLogin(View view){
       startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void irAhTelaCadastro(View view){
        startActivity(new Intent(MainActivity.this, CadastroActivity.class));
    }
}