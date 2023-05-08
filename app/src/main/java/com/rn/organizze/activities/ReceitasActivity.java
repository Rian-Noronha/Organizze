package com.rn.organizze.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rn.organizze.R;
import com.rn.organizze.config.ConfiguracaoFirebase;
import com.rn.organizze.helper.Base64Custom;
import com.rn.organizze.helper.DateCustom;
import com.rn.organizze.model.Movimentacao;
import com.rn.organizze.model.Usuario;

public class ReceitasActivity extends AppCompatActivity {

    private EditText campoValor;
    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private Movimentacao movimentacao;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
    private Double receitaTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        campoValor          = findViewById(R.id.editValorRceitas);
        campoData           = findViewById(R.id.editDataReceitas);
        campoCategoria      = findViewById(R.id.editCategoriaReceitas);
        campoDescricao      = findViewById(R.id.editDescricaoReceitas);


        campoData.setText(DateCustom.dataAtual());

        recuperarReceitaTotal();

    }

    public void salvarReceita(View view){

        String valor        = campoValor.getText().toString();
        String data         = campoData.getText().toString();
        String categoria    = campoCategoria.getText().toString();
        String descricao    = campoDescricao.getText().toString();

        if(validarCampos(data, categoria, descricao, valor)){

            Double valorRecuperado = Double.parseDouble(valor);



            movimentacao = new Movimentacao();
            movimentacao.setValor(valorRecuperado);
            movimentacao.setData(data);
            movimentacao.setCategoria(categoria);
            movimentacao.setDescricao(descricao);
            movimentacao.setTipo("r");

            Double receitaAtualizada = valorRecuperado + receitaTotal;
            atualizarReceita(receitaAtualizada);
            movimentacao.salvar(data);

            finish();


        }


    }

    public void recuperarReceitaTotal(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario    = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaTotal    = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void atualizarReceita(Double receitaAtualizada){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);

        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        usuarioRef.child("receitaTotal").setValue(receitaAtualizada);


    }

    public boolean validarCampos(String campoData, String campoCategoria, String campoDescricao, String campoValor){
        boolean camposValidados = false;

        if(!campoData.isEmpty() && campoData != null){
            if(!campoCategoria.isEmpty() && campoCategoria != null){
                if(!campoDescricao.isEmpty() && campoDescricao != null){
                    if(!campoValor.isEmpty() && campoValor != null){
                        camposValidados = true;
                    }else{
                        mostrarToast("Problema com o valor:/");
                    }
                }else{
                    mostrarToast("Bote a descrição:)");
                }
            }else{
                mostrarToast("Bote a categoria:)");
            }
        }else{
            mostrarToast("Problema com a data:/");
        }

        return camposValidados;
    }

    public void mostrarToast(String mensagem){
        Toast.makeText(getApplicationContext(),
                mensagem, Toast.LENGTH_LONG).show();
    }


}